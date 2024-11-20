<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/admin/templates/header.jsp" %>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <title>게시글 승인</title>
    <link rel="stylesheet" type="text/css" href="/css/admin/adminApproval.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="/js/admin/adminApproval.js"></script>
  </head>
  <body>
    <div class="main-content">
      <h2>게시글 승인</h2>

      <div class="search-box">
        <form method="get" action="/admin/approval">
          <select name="column">
            <option value="id">ID</option>
            <option value="store_name">가게 이름</option>
            <option value="address">주소</option>
            <option value="tel">전화번호</option>
            <option value="status">상태</option>
          </select>
          <input type="text" name="keyword" placeholder="검색" />
          <button>검색</button>
        </form>
      </div>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>가게 이름</th>
            <th>주소</th>
            <th>전화번호</th>
						<th class="p-0">
              <select class="tab-select" name="enrollStatus">
                <option value="" ${enrollStatus == '' ? 'selected="selected"' : ''}>전체</option>
                <option value="wait" ${enrollStatus == 'wait' ? 'selected="selected"' : ''}>신청</option>
                <option value="read" ${enrollStatus == 'read' ? 'selected="selected"' : ''}>검토</option>
                <option value="enrolled" ${enrollStatus == 'enrolled' ? 'selected="selected"' : ''}>승인</option>
                <option value="withdrawal" ${enrollStatus == 'withdrawal' ? 'selected="selected"' : ''}>반려</option>
              </select>
            </th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="store" items="${pagination.content}">
            <tr>
              <td>${store.id}</td>
              <td>
                <a href="#" class="approval-link" data-id="${store.id}">${store.storeName} </a>
              </td>
              <td>${store.address}</td>
              <td>${store.tel}</td>
              <td class="approval-status" data-id="${store.id}" data-status="${store.enrollStatus}">${store.enrollStatus}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- Pagination -->
      <div class="pagination">
        <!-- 클래스명을 동적으로 변경 -->
        <div class="move-page-link">
          <a class="page-link ${pagination.isFirstPageBtnVisible() ? '' : 'disabled'}" href="/admin/approval?page=1">처음</a>
          <a class="page-link ${pagination.page > 1 ? '' : 'disabled'}" href="/admin/approval?page=${pagination.page - 1}">이전</a>
        </div>

        <div class="page">
          <c:forEach var="page" begin="${pagination.start}" end="${pagination.end}">
            <c:choose>
              <c:when test="${page == pagination.page}">
                <span class="current-page">${page}</span>
              </c:when>
              <c:otherwise>
                <a class="page-link" href="/admin/approval?page=${page}">${page}</a>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>

        <div class="move-page-link">
          <a class="page-link ${pagination.page < pagination.totalPages ? '' : 'disabled'}" href="/admin/approval?page=${pagination.page + 1}">다음</a>
          <a class="page-link ${pagination.isLastPageBtnVisible() ? '' : 'disabled'}" href="/admin/approval?page=${pagination.totalPages}">끝</a>
        </div>
      </div>
    </div>

    <!-- 가게 등록 폼 팝업 -->
    <div class="popup-overlay" id="popup-overlay">
      <div class="popup-content">
        <button class="popup-close" onclick="closePopup()">X</button>
        <div id="approval-content"></div>
      </div>
    </div>
  </body>
</html>
