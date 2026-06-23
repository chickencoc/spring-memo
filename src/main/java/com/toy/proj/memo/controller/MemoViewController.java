package com.toy.proj.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.proj.common.ComField;
import com.toy.proj.member.model.Member;
import com.toy.proj.memo.model.Memo;
import com.toy.proj.memo.service.MemoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoViewController {

    private final MemoService memoService;
    
    @GetMapping({"/", ""})
    public String main() {
    	return "redirect:/memo/list";
    }

    @GetMapping("/list")
    public String memoList(Model model, @RequestParam(name="q", required = false) String keyword) {
        
    	if(keyword == null || keyword.isBlank()) {
    		model.addAttribute("memoList", memoService.getAllMemo());    	
    	} else {
    		model.addAttribute("memoList", memoService.searchMemo(keyword));    		
    	}
    	
    	model.addAttribute("q", keyword);
    	return "list";
    }
    
    @GetMapping("/view/{seq}")
    public String memoView(HttpSession session, Model model, @PathVariable Integer seq, @RequestParam(name="q", required = false) String keyword) {
    	
    	Memo memo = memoService.getMemo(seq);
		boolean chkAuth = memoService.canEditMemo( (Member) session.getAttribute(ComField.SES_USER), memo);
    	
		model.addAttribute("memo", memo);
		model.addAttribute("q", keyword);
		model.addAttribute("chkAuth", chkAuth);
		model.addAttribute("writerDisplayName", memoService.getMemberDisplayName(memo.getCrmid()));
		model.addAttribute("updaterDisplayName", memoService.getMemberDisplayName(memo.getUpmid()));
				
    	return "detail";
    }
    
    @GetMapping("/write")
    public String writeView(Model model) {
    	
    	model.addAttribute("memo", new Memo());
    	return "write";
    }
    
    @GetMapping("/edit/{seq}")
    public String editView(HttpSession session, Model model, @PathVariable Integer seq, @RequestParam(name="q", required = false) String keyword) {
    	
    	Memo memo = memoService.getMemo(seq);
		boolean chkAuth = memoService.canEditMemo( (Member) session.getAttribute(ComField.SES_USER), memo);
    	
    	if(!chkAuth)
    		return "redirect:/memo/view/" + seq;
    	
    	model.addAttribute("memo", memoService.getMemo(seq));
    	model.addAttribute("q", keyword);
		
    	return "edit";
    }
    
    @PostMapping("/view")
    public String createMemo(HttpServletRequest request, Memo memo) {
    	
    	Member member = (Member) request.getSession().getAttribute( ComField.SES_USER );
    	
    	memo.setCrmid(member.getUid());
    	memo.setUpmid(member.getUid());
    	
    	Memo newMemo = memoService.createMemo(memo);
    	return "redirect:/memo/view/" + newMemo.getSerial();
    }
}
