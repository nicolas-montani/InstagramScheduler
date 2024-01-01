package com.example.instagramscheduler.views;

import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.services.CampaignService;
import com.example.instagramscheduler.services.PostService;
import com.example.instagramscheduler.services.UserService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Create Post")
@Route(value = "Post", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class PostView extends AppLayout {


    private final UserService userService;
    private final PostService postService;

    private final CampaignService campaignService;

    private final Grid<Post> postGrid = new Grid<>(Post.class);

    public PostView(UserService userService, CampaignService campaignService , PostService postService) {
        this.userService = userService;
        this.campaignService = campaignService;
        this.postService = postService;

        updatePostList();
        setContent(createContentLayout());

    }

    //CONTENT LAYOUT
    private HorizontalLayout createContentLayout() {
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.addClassName("content-layout");
        contentLayout.setSizeFull();
        contentLayout.setPadding(false);
        contentLayout.setSpacing(false);

        contentLayout.add(createPostGrid());

        return contentLayout;
    }

    private VerticalLayout createPostGrid() {


        VerticalLayout campaignLayout = new VerticalLayout();
        campaignLayout.setSizeFull();
        campaignLayout.setPadding(false);
        campaignLayout.setWidth("100em");

        postGrid.setSizeFull();
        postGrid.setColumns( "id", "caption", "imageUrl", "scheduledTime", "campaign");


        postGrid.addComponentColumn(item -> {
            return new Button("Edit", clickEvent -> {
                Dialog dialog = new Dialog();
                dialog.add(new H3("Edit Post"));
                FormLayout form = new FormLayout();
                TextField caption = new TextField("Caption");
                caption.setValue(item.getCaption());
                form.add(caption);
                Button save = new Button("Save", event -> {
                    item.setCaption(caption.getValue());
                    postService.save(item);
                    updatePostList();
                    dialog.close();
                });
                Button cancel = new Button("Cancel", event -> {
                    dialog.close();
                });
                HorizontalLayout buttons = new HorizontalLayout(save, cancel);
                dialog.add(form, buttons);
                dialog.open();
            });
        }).setHeader("Actions");
        Button newCampaignButton = new Button("New Post", clickEvent -> {
            Dialog dialog = new Dialog();
            dialog.add(new H3("New Campaign"));
            FormLayout form = new FormLayout();
            TextField caption= new TextField("Caption");
            
            form.add(caption);
            Button save = new Button("Save", event -> {
                Campaign newCampaign = new Campaign();
                newCampaign.setCampaign_name(caption.getValue());
                campaignService.save(newCampaign);
                updatePostList();
                dialog.close();
            });
            Button delete = new Button("Delete", event -> {
                Campaign newCampaign = new Campaign();
                newCampaign.setCampaign_name(caption.getValue());
                campaignService.save(newCampaign);
                updatePostList();
                dialog.close();
            });
            Button cancel = new Button("Cancel", event -> {
                dialog.close();
            });
            HorizontalLayout buttons = new HorizontalLayout(save, delete, cancel);
            dialog.add(form, buttons);
            dialog.open();
        });
        campaignLayout.add(postGrid, newCampaignButton);
        return campaignLayout;
    }

    private void updatePostList() {
        postGrid.setItems(postService.findAll());
    }


}
