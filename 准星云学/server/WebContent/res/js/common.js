/**
 * 共同的JS
 * <p>
 * 处理显示用户卡片、人员卡片、联系人卡片等.
 * </p>
 */

/**
 * 显示用户信息卡片.
 */
function showPersion(persionId) {
	 jQuery.gritter.add({
         title: '用户信息!',
         text: '点击这里将显示用户的卡片信息.',
         class_name: 'growl-info',
		 image: '../bootstrap/images/screen.png',
         sticky: false,
         time: ''
  });
}

/**
 * 显示联系人卡片信息.
 * @param contactsId - 联系人ID
 */
function showContacts(contactsId) {
	 jQuery.gritter.add({
         title: '联系人信息!',
         text: '点击这里将显示联系人的卡片信息.',
         class_name: 'growl-info',
		 image: '../bootstrap/images/screen.png',
         sticky: false,
         time: ''
  });

}

/**
 * 显示客户信息卡片.
 * @param companyId
 */
function showCompany(companyId) {
	 jQuery.gritter.add({
         title: '公司信息!',
         text: '点击这里讲显示公司的卡片信息.',
         class_name: 'growl-info',
		 image: '../bootstrap/images/screen.png',
         sticky: false,
         time: ''
  });

}
/**
 * 随机产生32位数字
 * @param len
 * @returns {String}
 */
function randomString(len) {
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
	/** **默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1*** */
	var maxPos = $chars.length;
	var pwd = '';
	for (var i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}
