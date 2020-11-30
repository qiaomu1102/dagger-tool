package com.springdagger.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 */
public class DaggerConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile DaggerConversionService SHARED_INSTANCE;

	public DaggerConversionService() {
		this(null);
	}

	public DaggerConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link DaggerConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code DaggerConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		DaggerConversionService sharedInstance = DaggerConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (DaggerConversionService.class) {
				sharedInstance = DaggerConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new DaggerConversionService();
					DaggerConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
