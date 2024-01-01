package com.example.instagramscheduler.views.components;

import com.example.instagramscheduler.model.User;
import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.services.CampaignService;
import com.example.instagramscheduler.services.PostService;
import com.example.instagramscheduler.services.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class PostForm  extends FormLayout{

    BeanValidationBinder<Post> binder = new BeanValidationBinder<>(Post.class);
    TextField caption = new TextField("Caption");
    TextField imageUrl = new TextField("imageUrl");
    DatePicker post_date = new DatePicker("Post Date");

    public PostForm(List<Campaign> campaign, List<Post> post){
        addClassName("post-form");
        add(caption, imageUrl, post_date);
        binder.bindInstanceFields(this);
    }
    public void setPost(Post post){
        binder.setBean(post);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener){
        return addListener(SaveEvent.class, listener);
    }
    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener){
        return addListener(DeleteEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener){
        return addListener(CloseEvent.class, listener);
    }

    private Component createButtonsLayout(Post post){
        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> validateAndSave());

        Button delete = new Button("Delete");
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, post)));

        Button close = new Button("Cancel");
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }
    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    @Getter
    public static abstract class PostFormEvent extends ComponentEvent<PostForm>{
        private final Post post;
        protected PostFormEvent(PostForm source, Post post){
            super(source, false);
            this.post = post;
        }
    }
    public static class SaveEvent extends PostFormEvent{
        SaveEvent(PostForm source, Post post){
            super(source, post);
        }
    }
    public static class DeleteEvent extends PostFormEvent{
        DeleteEvent(PostForm source, Post post){
            super(source, post);
        }
    }

    public static class CloseEvent extends PostFormEvent{
        CloseEvent(PostForm source){
            super(source, null);
        }
    }

}
