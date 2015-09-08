/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shop.plugin.PaymentPlugin;
import net.shop.plugin.StoragePlugin;
import net.shop.service.PluginService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

/**
 * Service - 插件
 * 
 * 
 * 
 */
@Service("pluginServiceImpl")
public class PluginServiceImpl implements PluginService {

	@Resource
	private List<PaymentPlugin> paymentPlugins = new ArrayList<PaymentPlugin>();
	@Resource
	private List<StoragePlugin> storagePlugins = new ArrayList<StoragePlugin>();
	@Resource
	private Map<String, PaymentPlugin> paymentPluginMap = new HashMap<String, PaymentPlugin>();
	@Resource
	private Map<String, StoragePlugin> storagePluginMap = new HashMap<String, StoragePlugin>();

	@Override
	public List<PaymentPlugin> getPaymentPlugins() {
		Collections.sort(paymentPlugins);
		return paymentPlugins;
	}

	@Override
	public List<StoragePlugin> getStoragePlugins() {
		Collections.sort(storagePlugins);
		return storagePlugins;
	}

	@Override
	public List<PaymentPlugin> getPaymentPlugins(final boolean isEnabled) {
		List<PaymentPlugin> result = new ArrayList<PaymentPlugin>();
		CollectionUtils.select(paymentPlugins, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				PaymentPlugin paymentPlugin = (PaymentPlugin) object;
				return paymentPlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	@Override
	public List<StoragePlugin> getStoragePlugins(final boolean isEnabled) {
		List<StoragePlugin> result = new ArrayList<StoragePlugin>();
		CollectionUtils.select(storagePlugins, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				StoragePlugin storagePlugin = (StoragePlugin) object;
				return true;//storagePlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	@Override
	public PaymentPlugin getPaymentPlugin(String id) {
		return paymentPluginMap.get(id);
	}

	@Override
	public StoragePlugin getStoragePlugin(String id) {
		return storagePluginMap.get(id);
	}

}