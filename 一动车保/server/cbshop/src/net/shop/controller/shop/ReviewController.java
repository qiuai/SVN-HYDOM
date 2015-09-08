/*
 * 
 * 
 * 
 */
package net.shop.controller.shop;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.shop.Message;
import net.shop.Pageable;
import net.shop.ResourceNotFoundException;
import net.shop.Setting;
import net.shop.Setting.CaptchaType;
import net.shop.Setting.ReviewAuthority;
import net.shop.entity.Member;
import net.shop.entity.Product;
import net.shop.entity.Review;
import net.shop.service.CaptchaService;
import net.shop.service.MemberService;
import net.shop.service.ProductService;
import net.shop.service.ReviewService;
import net.shop.util.SettingUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 评论
 * 
 * 
 * 
 */
@Controller("shopReviewController")
@RequestMapping("/review")
public class ReviewController extends BaseController {

	/** 每页记录数 */
	private static final int PAGE_SIZE = 10;

	@Resource(name = "reviewServiceImpl")
	private ReviewService reviewService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;

	/**
	 * 发表
	 */
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public String add(@PathVariable Long id, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute("product", product);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/review/add";
	}

	/**
	 * 内容
	 */
	@RequestMapping(value = "/content/{id}", method = RequestMethod.GET)
	public String content(@PathVariable Long id, Integer pageNumber, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			throw new ResourceNotFoundException();
		}
		Product product = productService.find(id);
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		model.addAttribute("product", product);
		model.addAttribute("page", reviewService.findPage(null, product, null, true, pageable));
		return "/shop/review/content";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Message save(String captchaId, String captcha, Long id, Integer score, String content, HttpServletRequest request) {
		if (!captchaService.isValid(CaptchaType.review, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsReviewEnabled()) {
			return Message.error("shop.review.disabled");
		}
		if (!isValid(Review.class, "score", score) || !isValid(Review.class, "content", content)) {
			return ERROR_MESSAGE;
		}
		Product product = productService.find(id);
		if (product == null) {
			return ERROR_MESSAGE;
		}
		Member member = memberService.getCurrent();
		if (setting.getReviewAuthority() != ReviewAuthority.anyone && member == null) {
			return Message.error("shop.review.accessDenied");
		}
		if (setting.getReviewAuthority() == ReviewAuthority.purchased) {
			if (!productService.isPurchased(member, product)) {
				return Message.error("shop.review.noPurchased");
			}
			if (reviewService.isReviewed(member, product)) {
				return Message.error("shop.review.reviewed");
			}
		}
		Review review = new Review();
		review.setScore(score);
		review.setContent(content);
		review.setIp(request.getRemoteAddr());
		review.setMember(member);
		review.setProduct(product);
		if (setting.getIsReviewCheck()) {
			review.setIsShow(false);
			reviewService.save(review);
			return Message.success("shop.review.check");
		} else {
			review.setIsShow(true);
			reviewService.save(review);
			return Message.success("shop.review.success");
		}
	}

}