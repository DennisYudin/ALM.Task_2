package dev.andrylat.task2.controllers;

import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class DemoController {

    @Autowired
    private CategoryDAO categoryDAO;

    @RequestMapping("/list")
    public String listCategories(Model theModel) {

        List<Category> theCategories = categoryDAO.getCategories();

        theModel.addAttribute("categories", theCategories);

        return "list-categories";
    }
}


