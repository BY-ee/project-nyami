package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.domain.ReviewDomain;

import com.project.domain.Review;


@Mapper
public interface ReviewMapper {

	// 모든 리뷰 조회
	List<ReviewDomain> getReviewsByStoreId(int storeId);

	// 리뷰 삽입
	void insertReview(ReviewDomain newReview);
	List<Review> selectReviews(@Param("start") int start, @Param("end") int end);


}