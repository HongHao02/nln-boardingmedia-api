package com.b2012202.mxhtknt.Services;

import com.b2012202.mxhtknt.Request.BaiVietRequest;
import com.b2012202.mxhtknt.Request.ResponseObject;

public interface BaiVietService {
    ResponseObject createBaiViet(BaiVietRequest baiVietRequest);

    ResponseObject getByIdBaiViet(Long idBaiViet);
//    ResponseObject readDetailFile(String fileName);

    byte[] getFileByte(String fileName);

    ResponseObject getBaiVietListFollowPage(int page, int size);

    ResponseObject deleteBaiViet(Long idBaiViet);
}
