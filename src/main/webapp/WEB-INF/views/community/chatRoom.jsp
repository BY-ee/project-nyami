<!-- community.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>채팅방 관리</title>
    <jsp:include page="/WEB-INF/views/templates/head.jsp" /> <!-- header -->
</head>
<body>

    <jsp:include page="/WEB-INF/views/templates/header.jsp" /> <!-- header -->

    <h1>채팅방 관리</h1>
    <!-- 채팅방 생성 팝업 -->
    <div id="create-form-popup" style="display: none;">
        <h2>채팅방 생성</h2>
        <form id="chat-room-form">
            <label for="roomName">채팅방 이름:</label>
            <input type="text" id="roomName" name="roomName" required><br><br>
            <label for="location">지역:</label>
            <input type="text" id="location" name="location"><br><br>
            <label for="maxParticipants">정원:</label>
            <input type="number" id="maxParticipants" name="maxParticipants" required><br><br>
            <label for="theme">테마:</label>
            <input type="text" id="theme" name="theme"><br><br>
            <label for="recruitmentDuration">모집 기간(분):</label>
            <input type="number" id="recruitmentDuration" name="recruitmentDuration" required><br><br>
            <label for="additionalDescription">추가 설명:</label>
            <textarea id="additionalDescription" name="additionalDescription"></textarea><br><br>
            <button type="submit">생성</button>
            <button type="button" id="cancel-create-popup">취소</button>
        </form>
    </div>
    <div id="create-form-popup-overlay" style="display: none;"></div>
    
    <!-- 채팅방 목록 -->
    <div id="chat-room-list">
        <h2>채팅방 목록</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>채팅방 이름</th>
                    <th>지역</th>
                    <th>정원</th>
                    <th>테마</th>
                    <th>마감 시간</th>
                    <th>생성자</th>
                    <th>액션</th>
                </tr>
            </thead>
            <tbody id="chat-room-table-body"></tbody>
        </table>
    </div>
    
    <button id="show-create-popup">채팅방 생성</button>

</body>
</html>