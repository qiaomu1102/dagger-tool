package com.springdagger.core.mp.base;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface BaseService<T> extends IService<T> {

	/**
	 * 逻辑删除
	 *
	 * @param ids id集合(逗号分隔)
	 * @return boolean
	 */
	boolean deleteLogic(@NotEmpty List<Long> ids);

}
