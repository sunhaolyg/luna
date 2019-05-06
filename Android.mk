LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := \
  android-support-v4 \
  okhttp3 \
  okio \
  android-support-v7-recyclerview \
  fastjson \
  pdf-view \
  dom \
  jsoup \
  picasso \
 #pdf-ium \

LOCAL_PACKAGE_NAME := Luna
LOCAL_CERTIFICATE := platform
LOCAL_PRIVILEGED_MODULE := true

#LOCAL_JAVA_LIBRARIES := mediatek-framework
LOCAL_PROGUARD_FLAG_FILES := proguard.flags

LOCAL_RESOURCE_DIR :=  $(LOCAL_PATH)/res
LOCAL_SRC_FILES := $(call all-java-files-under, src)

#include frameworks/opt/setupwizard/library/common-gingerbread.mk

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := \
    pdf-view:libs/android-pdf-viewer-2.6.0-sources.jar \
    dom:libs/dom4j-2.1.1.jar \
    jsoup:libs/jsoup-1.11.3.jar \
    picasso:libs/picasso-2.0.0.jar \
    #pdf-ium:libs/pdfium-android-1.7.1-sources.jar \

include $(BUILD_MULTI_PREBUILT)

#include $(call all-makefiles-under,$(LOCAL_PATH))