package com.github.nnh2.presentationlayer.fragments.info;

import com.github.nnh2.datalayer.entity.ImageProcessData;
import com.github.nnh2.datalayer.eventbus.ImageInfoEvent;
import com.github.nnh2.datalayer.eventbus.ImageProcessResponse;
import com.github.nnh2.presentationlayer.fragments.core.BaseView;

import java.util.List;

/**
 * Created by Zahar on 03.04.16.
 */

public interface InfoView extends BaseView {

	void setViewCount(ImageInfoEvent count);

	void setResponse(List<ImageProcessData> imageInfoEvent);
}