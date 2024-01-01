package com.example.instagramscheduler.views;

import com.example.instagramscheduler.views.components.PostForm;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.instagramscheduler.services.UserService;
import com.example.instagramscheduler.model.Post;
import com.example.instagramscheduler.services.PostService;
import com.example.instagramscheduler.model.Campaign;
import com.example.instagramscheduler.services.CampaignService;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDateTime;
import java.util.List;

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

    private PostForm postForm;
    private Long postId;


    public CampaignView(UserService userService, CampaignService campaignService , PostService postService) {
        this.userService = userService;
        this.campaignService = campaignService;
        this.postService = postService;

        updateCampaignList();
        updatePostList();
        setContent(createContentLayout());

    }

    //CONTENT LAYOUT

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

    // TODO   : Correct this
    private HorizontalLayout createPostGrid() {

        postForm = new PostForm(campaignService.findAll(), postService.findAll());
        postForm.setWidth("25em");
        postForm.addSaveListener(this::saveItem);
        postForm.addDeleteListener(this::deleteItem);
        postForm.addCloseListener(e -> closeItemEditor());
        closeItemEditor();

        // Search field
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search items");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> updateGridFilter(e.getValue()));

        HorizontalLayout toolbar = new HorizontalLayout(searchField);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setWidthFull();

        post.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        post.setSizeFull();
        post.setColumns("caption", "imageUrl", "scheduledTime");

        post.addComponentColumn(item -> {
            return new Button("Edit", clickEvent -> {
                Dialog dialog = new Dialog();
                dialog.add(new H3("Edit Post"));
                FormLayout form = new FormLayout();
                TextField caption = new TextField("Caption");
                caption.setValue(item.getCaption());
                TextField image = new TextField("Image");
                image.setValue(item.getImageUrl());
                TextField date = new TextField("Date");
                date.setValue(String.valueOf(item.getScheduledTime()));
                TextField time = new TextField("Time");
                time.setValue(String.valueOf(item.getScheduledTime()));
                ComboBox<Campaign> campaign = new ComboBox<>("Campaign");
                campaign.setItems(campaignService.findAll());
                campaign.setItemLabelGenerator(Campaign::getCampaign_name);
                campaign.setValue(item.getCampaign());
                form.add(caption, image, date, time, campaign);
                Button save = new Button("Save", event -> {
                    item.setCaption(caption.getValue());
                    item.setImageUrl(image.getValue());
                    item.setScheduledTime(LocalDateTime.parse(date.getValue() + "T" + time.getValue()));
                    item.setCampaign(campaign.getValue());
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

        post.getColumns().forEach(col -> col.setAutoWidth(true));
        post.asSingleSelect().addValueChangeListener(event -> editPost(event.getValue()));

        Button addPostButton = new Button("Add Post", event -> editPost(new Post()));
        addPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout postLayout = new HorizontalLayout(post, addPostButton);
        postLayout.add(postForm);
        postLayout.setFlexGrow(2, post);
        postLayout.setFlexGrow(1, postForm);
        postLayout.setSizeFull();

        return postLayout;
    }

    //EVENT LISTENERS

    private void saveItem(PostForm.SaveEvent event) {
        Post post = event.getPost();
        postService.save(post);
        updatePostList();
        closeItemEditor();
    }

    private void editPost(Post post) {
        if (post == null) {
            closeItemEditor();
        } else {
            postForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteItem(PostForm.DeleteEvent event) {
        postService.delete(event.getPost().getId());
        updatePostList();
        closeItemEditor();
    }

    private void closeItemEditor() {
        postForm.setVisible(false);
        removeClassName("editing");
    }






    //UPDATE VIEW
    private void updatePostList() {
        List<Post> posts = postService.findAll();
        post.setItems(posts);
    }


    private void updateCampaignList() {
        List<Campaign> campaigns = campaignService.findAll();
        campaign.setItems(campaigns);
    }

    private void updateGridFilter(String filterText) {
        if (postId == null) {
            return;
        }

        ShoppingList shoppingList = shoppingListService.getShoppingListById(shoppingListId).orElse(null);
        if (shoppingList == null) {
            return;
        }
        // search for name, description, category or store containing the filter text
        List<ShoppingItem> filteredItems = shoppingList.getShoppingItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(filterText.toLowerCase())
                        || (item.getDescription() != null && item.getDescription().toLowerCase().contains(filterText.toLowerCase()))
                        || (item.getCategory() != null && item.getCategory().getName().toLowerCase().contains(filterText.toLowerCase()))
                        || (item.getStore() != null && item.getStore().getName().toLowerCase().contains(filterText.toLowerCase())))
                .collect(Collectors.toList());

        grid.setItems(filteredItems);
    }

    //New Campaign DIalog





}
