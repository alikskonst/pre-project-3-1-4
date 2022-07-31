package edu.kata.task314.controller.web.impl;

import edu.kata.task314.controller.web.ErrorWebController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ErrorWebControllerImpl implements ErrorWebController {

    @Override
    public String entityNotFoundException(final Throwable throwable, final ModelMap modelMap) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        modelMap.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @Override
    public String runtimeException(final Throwable throwable, final ModelMap modelMap) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        modelMap.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}