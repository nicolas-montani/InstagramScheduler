package com.example.instagramscheduler.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import com.example.instagramscheduler.model.User;
import com.example.instagramscheduler.security.AuthenticatedUser;
import com.example.instagramscheduler.services.UserService;
import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.services.PostService;
import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.services.CampaignService;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.stream.Collectors;

@PageTitle("ScheduleAdmin")
@Route(value = "ScheduleAdmin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class CampaignView extends AppLayout {

    private final UserService userService;
    private final PostService postService;

    private final CampaignService campaignService;

    private final Grid<Campaign> campaign = new Grid<>(Campaign.class);
    private final Grid<Post> post = new Grid<>(Post.class);



    public CampaignView(UserService userService, CampaignService campaignService , PostService postService) {
        this.userService = userService;
        this.campaignService = campaignService;
        this.postService = postService;

        updateCampaignList();
        //updatePostList();
        setContent(createContentLayout());

    }

    private HorizontalLayout createContentLayout() {
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSizeFull();
        contentLayout.setPadding(false);
        contentLayout.add(createCampaignList());
        contentLayout.add(createPostGrid());
        return contentLayout;
    }

    private VerticalLayout createCampaignList() {
        VerticalLayout campaignLayout = new VerticalLayout();
        campaignLayout.setSizeFull();
        campaignLayout.setPadding(false);
        campaignLayout.setWidth("350px");

        campaign.setColumns("campaign_name");
        campaign.addComponentColumn(item -> {
            return new Button("Edit", clickEvent -> {
                Dialog dialog = new Dialog();
                dialog.add(new H3("Edit Campaign"));
                FormLayout form = new FormLayout();
                TextField campaignName = new TextField("Campaign Name");
                campaignName.setValue(item.getCampaign_name());
                form.add(campaignName);
                Button save = new Button("Save", event -> {
                    item.setCampaign_name(campaignName.getValue());
                    campaignService.save(item);
                    updateCampaignList();
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
        Button newCampaignButton = new Button("New Campaign", clickEvent -> {
            Dialog dialog = new Dialog();
            dialog.add(new H3("New Campaign"));
            FormLayout form = new FormLayout();
            TextField campaignName = new TextField("Campaign Name");
            form.add(campaignName);
            Button save = new Button("Save", event -> {
                Campaign newCampaign = new Campaign();
                newCampaign.setCampaign_name(campaignName.getValue());
                campaignService.save(newCampaign);
                updateCampaignList();
                dialog.close();
            });
            Button cancel = new Button("Cancel", event -> {
                dialog.close();
            });
            HorizontalLayout buttons = new HorizontalLayout(save, cancel);
            dialog.add(form, buttons);
            dialog.open();
        });
        campaignLayout.add(campaign, newCampaignButton);
        return campaignLayout;
    }

    private HorizontalLayout createPostGrid() {
        HorizontalLayout postLayout = new HorizontalLayout();
        postLayout.setSizeFull();

        return postLayout;
    }

    private void updateCampaignList() {
        List<Campaign> campaigns = campaignService.findAll();
        campaign.setItems(campaigns);
    }
}
