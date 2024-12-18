package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.domain.Event;
import com.project.domain.Notice;
import com.project.dto.PageRequest;
import com.project.dto.PageResponse;
import com.project.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {
	
	private final NoticeService noticeService;

	@GetMapping("/noticeList")
	public String noticeList(Model model, PageRequest noticePageRequest) {
		PageResponse<Notice> noticePageResponse = noticeService.getNoticeList(noticePageRequest);
		System.out.println(noticePageResponse.getSearchKeyword());
		model.addAttribute("noticePageResponse",noticePageResponse);
		return "notice/noticeList";
	}
	
	@GetMapping("/notice/{id}")
	public String notice(@PathVariable("id")Long id,Model model) {
		
		Notice notice = noticeService.getNotice(id);
		notice.setViews(notice.getViews()+1);
		int i = noticeService.updateNoticeViews(notice);
		Notice preNotice = noticeService.getPreNotice(id);
		Notice nextNotice = noticeService.getNextNotice(id);
		model.addAttribute("notice",notice);
		model.addAttribute("preNotice",preNotice);
		model.addAttribute("nextNotice",nextNotice);
		return "notice/notice";
	}
	
	@GetMapping("/eventOnList")
	public String eventList(Model model, PageRequest eventPageRequest) {
		eventPageRequest.setSize(12);
		PageResponse<Event> eventPageResponse = noticeService.getEventOnList(eventPageRequest);
		model.addAttribute("eventPageResponse",eventPageResponse);
		return "notice/eventOnList";
	}
	
	@GetMapping("/eventOffList")
	public String eventOffList(Model model,PageRequest eventPageRequest) {
		eventPageRequest.setSize(12);
		eventPageRequest.setStatus("inactive");
		PageResponse<Event> eventPageResponse = noticeService.getEventOnList(eventPageRequest);
		model.addAttribute("eventPageResponse",eventPageResponse);
		return "notice/eventOffList";
	}
	
	@GetMapping("/event/{id}")
	public String event(@PathVariable("id")Long id,Model model) {
		Event event = noticeService.getEvent(id);
		event.setViews(event.getViews()+1);
		int i = noticeService.updateEventViews(event);
		Event preEvent = noticeService.getPreEvent(id);
		Event nextEvent = noticeService.getNextEvent(id);
		model.addAttribute("event",event);
		model.addAttribute("preEvent",preEvent);
		model.addAttribute("nextEvent",nextEvent);
		return "notice/event";
	}
}
