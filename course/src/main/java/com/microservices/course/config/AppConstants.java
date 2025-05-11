package com.microservices.course.config;

import java.io.File;

public class AppConstants {

    public static  final String COURSE_BANNER_OUTPUT = "uploads"+ File.separatorChar+"courses" + File.separatorChar+"banners";

    public static final String CATEGORY_URL = "http://category-service/api/v1/categories/";

    public  static  final String VIDEO_URL ="http://video-service/api/v1/videos/";
}
