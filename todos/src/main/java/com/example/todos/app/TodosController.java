package com.example.todos.app;

import com.example.todos.domain.model.Todo;
import com.example.todos.domain.model.User;
import com.example.todos.service.TodoService;
import com.example.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("todos/{userId}")
public class TodosController {

    @Autowired
    TodoService todosService;

    @Autowired
    UserService userService;

    @ModelAttribute("todoForm")
    public TodoForm setUpTodoForm() {
        return new TodoForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listTodos(
            @PathVariable("userId") Integer userId,
            Model model) {
        List<Todo> todosList = todosService.getTodosByUserId(userId);
        model.addAttribute("todos", todosList);
        model.addAttribute("userId", userId);
        return "todo/todos";
    }

    @RequestMapping(value = "/formcreate", method = RequestMethod.GET)
    public String showCreateTodos(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "todo/formcreate";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(
            @Validated @ModelAttribute("todoForm") TodoForm form,
            BindingResult bindingResult,
            @PathVariable("userId") Integer userId,
            Model model) {

        Optional<User> optionalUser = userService.getUserFindById(userId);

        if (bindingResult.hasErrors()) {
            return showCreateTodos(userId, model);
        }
        Todo todos = new Todo();
        todos.setTitle(form.getTitle());              //入力されたtitleをtodosEntityにセット
        todos.setDescription(form.getDescription());  //入力されたdescriptionをtodosEntityにセット
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // OptionalからUserを取り出す
            todos.setUser(user); // Userオブジェクトを設定
        } else {
            // Userが見つからない場合の処理
            throw new RuntimeException("User not found with id: " + userId);
        }

        todos.setDueDate(todos.getDueDate());


        try {
            todosService.save(todos);
        } catch (DataAccessException e) {
            model.addAttribute("error", "データベースアクセスエラーが発生しました: " + e.getMessage());
            return showCreateTodos(userId, model);  // エラーページのビュー名を指定してください
        } catch (Exception e) {
            model.addAttribute("error", "予期しないエラーが発生しました: " + e.getMessage());
            return showCreateTodos(userId, model);  // エラーページのビュー名を指定してください
        }
        return "redirect:/todos/{userId}";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("userId") Integer userId, @PathVariable("id") Integer id, Model model) {
        Optional<Todo> optionalTodos = todosService.findByIdAndUserId(id, userId);
        if (optionalTodos.isPresent()) {
            Todo todos = optionalTodos.get();
            TodoForm form = new TodoForm();
            form.setTitle(todos.getTitle());
            form.setDescription(todos.getDescription());
            form.setDueDate(todos.getDueDate());

            model.addAttribute("todoForm", form);
            model.addAttribute("todoId", id);
            model.addAttribute("userId", userId);
            return "todo/edit";
        } else {
            throw new RuntimeException("Todo not found with id: " + id + " and userId: " + userId);
        }
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(
            @Validated @ModelAttribute("todoForm") TodoForm form,
            BindingResult bindingResult,
            @PathVariable("userId") Integer userId,
            @PathVariable("id") Integer id,
            Model model) {

        if (bindingResult.hasErrors()) {
            return showEditForm(userId, id, model);
        }

        Optional<Todo> optionalTodos = todosService.findByIdAndUserId(id, userId);
        if (optionalTodos.isPresent()) {
            Todo todos = optionalTodos.get();
            todos.setTitle(form.getTitle());
            todos.setDescription(form.getDescription());
            todos.setDueDate(form.getDueDate());
            try {
                todosService.save(todos);
            } catch (DataAccessException e) {
                model.addAttribute("error", "データベースアクセスエラーが発生しました: " + e.getMessage());
                return showEditForm(userId, id, model);
            } catch (Exception e) {
                model.addAttribute("error", "予期しないエラーが発生しました: " + e.getMessage());
                return showEditForm(userId, id, model);
            }
            return "redirect:/todos/{userId}";
        } else {
            throw new RuntimeException("Todo not found with id: " + id + " and userId: " + userId);
        }
    }
}
