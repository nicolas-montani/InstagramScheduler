package com.example.instagramscheduler.views.components;

import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.model.Campaign;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
public class PostForm  extends FormLayout{
    TextField caption = new TextField("Caption");
    TextField image = new TextField("Image");
    TextField date = new TextField("Date");
    TextField time = new TextField("Time");
    ComboBox<Campaign> campaign = new ComboBox<>("Campaign");
    ComboBox<Post> post = new ComboBox<>("Post");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public PostForm(List<Campaign> campaigns, List<Post> posts) {
        addClassName("contact-form");


        campaign.setItems(campaigns);
        campaign.setItemLabelGenerator(Campaign::getCampaign_name);

        post.setItems(posts);
        post.setItemLabelGenerator(Post::getCaption);

        add(caption, image, date, time, campaign,

        createButtonsLayout());
    }
    public void setPost(Post post) {
        caption.setValue(post.getCaption());
        image.setValue(post.getImageUrl());
        date.setValue(String.valueOf(post.getScheduledTime()));
        time.setValue(String.valueOf(post.getScheduledTime()));
        campaign.setValue(post.getCampaign());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }
}
