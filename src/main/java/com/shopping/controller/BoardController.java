package com.shopping.controller;

import com.shopping.entity.Board;
import com.shopping.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "/list")
    public String SelectAll(Model model){
        List<Board> boardList = boardService.SelectAll();

        model.addAttribute("list", boardList);

        return "/board/boardList";
    }
    
    @GetMapping(value = "/insert")
    public String doGetInsert(Model model){
        System.out.println("게시글 등록");
        model.addAttribute("board", new Board());
        return "/board/boardInsert";
    }

    @PostMapping(value = "/insert")
    public String doPostInsert(Board board, Model model){
        System.out.println("board : " + board);
        int cnt = -999;
        cnt = boardService.Insert(board);
        System.out.println("cnt : " + cnt);

        if(cnt ==1){
            return "redirect:/board/list";
        }else{
            return "/board/boardInsert";
        }
    }

    @GetMapping(value = "/detail/{no}")
    public String SelectOne(@PathVariable("no") Integer no, Model model){
        Board board = boardService.SelectOne(no);
        model.addAttribute("board", board);

        return "/board/boardDetail";
    }

    @GetMapping(value = "/delete/{no}")
    public String Delete(@PathVariable("no") Integer no){
        int cnt = -999;
        cnt = boardService.Delete(no);

        return "redirect:/board/list";
    }

    @GetMapping(value = "/update/{no}")
    public String doGetUpdate(@PathVariable("no") Integer no, Model model){
        Board board = boardService.SelectOne(no);
        model.addAttribute("board", board);

        return "/board/boardUpdate";
    }

    @PostMapping(value = "/update")
    public String doPostUpdate(Board board){
        System.out.println("board : " + board);
        int cnt = -999;
        cnt = boardService.Update(board);
        System.out.println("cnt : " + cnt);

        if(cnt ==1){
            return "redirect:/board/list";
        }else{
            return "/board/boardUpdate";
        }
    }
}
