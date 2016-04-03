package com.github.nnh2.datalayer.eventbus;

import com.github.nnh2.datalayer.entity.ImageProcessData;

import java.util.List;

/**
 * Created by Zahar on 03.04.16.
 */
public class ImageProcessResponse {

	private List<ImageProcessData> datas;

	public ImageProcessResponse(List<ImageProcessData> datas) {
		this.datas = datas;
	}

	public List<ImageProcessData> getDatas() {
		return datas;
	}

}
