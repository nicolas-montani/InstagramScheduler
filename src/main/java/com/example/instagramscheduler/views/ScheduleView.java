package com.example.instagramscheduler.views;

import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.services.PostService;
import com.example.instagramscheduler.views.components.PostForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PageTitle("ScheduleUser")
@Route(value = "Schedule", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ScheduleView extends VerticalLayout {
    Grid<Post> grid = new Grid<>(Post.class);
    TextField filterText = new TextField();
    PostForm form;

    public ScheduleView() {
        addClassName("schedule-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);


        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new PostForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");
    }

    private void configureGrid() {

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

    }

    private Component getToolbar() {
        Button addPostButton = new Button("Add post", click -> {
            grid.asSingleSelect().clear();
            form.setPost(new Post()); // Assuming Post has an empty constructor
        });

        HorizontalLayout toolbar = new HorizontalLayout(addPostButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }
}
