package com.project.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.domain.Review;
import com.project.domain.ReviewDomain;


@Mapper
public interface ReviewMapper {

	// 리뷰 조회
	public List<ReviewDomain> getReviewsByStoreId(int storeId);

	// 리뷰 삽입
	void insertReview(ReviewDomain newReview);
	
	// 리뷰 조회
	List<Review> selectReviews(@Param("start") int start, @Param("end") int end);

	// 리뷰 삭제
    void deleteReview(Map<String, Object> reviewDetails); 

	// 총 리뷰 개수
	long countReviews();

	// 특정 리뷰 확인
	public Review selectReviewById(long id);
}
