package com.hse.somport.somport.view;

import com.hse.somport.somport.dto.FeedConfigDetails;
import com.hse.somport.somport.dto.FeedConfigDto;
import com.hse.somport.somport.service.FeedConfigService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "feed-config", layout = MainLayout.class)
@PageTitle("Feed Config")
@AnonymousAllowed
public class FeedConfigView extends VerticalLayout {

    private final FeedConfigService service;

    private final Grid<FeedConfigDto> grid = new Grid<>(FeedConfigDto.class, false);

    private final NumberField feedCount = new NumberField("Кол-во корма");
    private final NumberField poolNumber = new NumberField("Номер бассейна");

    private final Button newBtn = new Button("Добавить новый");
    private final Button saveBtn = new Button("Сохранить");
    private final Button deleteBtn = new Button("Удалить");

    private final BeanValidationBinder<FeedConfigDto> binder =
            new BeanValidationBinder<>(FeedConfigDto.class);

    private FeedConfigDto current;

    @Autowired
    public FeedConfigView(FeedConfigService service) {
        this.service = service;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Страница по центру (как ты просил раньше)
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        configureGrid();
        configureForm();
        configureActions();

        VerticalLayout editorPanel = buildEditorPanel();

        HorizontalLayout content = new HorizontalLayout(grid, editorPanel);
        content.setSizeFull(); // width+height 100% от root
        content.setAlignItems(FlexComponent.Alignment.CENTER);

        // Grid — на всю доступную ширину (занимает всё, кроме панели)
        grid.setWidthFull();
        grid.setHeightFull();
        content.setFlexGrow(1, grid);

        // Панель формы — фикс ширина, не растягиваем
        editorPanel.setWidth("420px");
        editorPanel.setHeightFull();
        content.setFlexGrow(0, editorPanel);

        add(content);

        refreshGrid();
        editNew();
    }

    private void configureGrid() {
        grid.addColumn(FeedConfigDto::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(dto -> dto.getConfig() != null ? dto.getConfig().getFeedCount() : null)
                .setHeader("feedCount").setAutoWidth(true);
        grid.addColumn(dto -> dto.getConfig() != null ? dto.getConfig().getPoolNumber() : null)
                .setHeader("poolNumber").setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                edit(e.getValue());
            }
        });
    }

    private void configureForm() {
        feedCount.setStep(1);
        feedCount.setMin(0);
        feedCount.setValueChangeMode(ValueChangeMode.ON_CHANGE);

        poolNumber.setStep(1);
        poolNumber.setMin(0);
        poolNumber.setValueChangeMode(ValueChangeMode.ON_CHANGE);

        binder.forField(feedCount)
                .withConverter(
                        v -> v == null ? null : v.longValue(),
                        v -> v == null ? null : v.doubleValue(),
                        "Введите число"
                )
                .bind("config.feedCount");

        binder.forField(poolNumber)
                .withConverter(
                        v -> v == null ? null : v.longValue(),
                        v -> v == null ? null : v.doubleValue(),
                        "Введите число"
                )
                .bind("config.poolNumber");
    }

    private void configureActions() {
        newBtn.addClickListener(e -> editNew());

        saveBtn.addClickListener(e -> {
            if (current == null) return;

            if (!binder.writeBeanIfValid(current)) {
                Notification.show("Исправьте ошибки в форме");
                return;
            }

            FeedConfigDto saved;
            if (current.getId() == null) {
                saved = service.createConfig(current);
            } else {
                saved = service.updateConfigById(current.getId(), current);
            }

            refreshGrid();
            edit(saved);
            Notification.show("Сохранено");
        });

        deleteBtn.addClickListener(e -> {
            if (current == null || current.getId() == null) {
                Notification.show("Нечего удалять");
                return;
            }
            service.deleteConfigById(current.getId());
            refreshGrid();
            editNew();
            Notification.show("Удалено");
        });
    }

    private VerticalLayout buildEditorPanel() {
        VerticalLayout panel = new VerticalLayout(buildButtons(), buildFormLayout());
        panel.setPadding(false);
        panel.setSpacing(true);
        panel.setAlignItems(FlexComponent.Alignment.STRETCH);
        return panel;
    }

    private FormLayout buildFormLayout() {
        FormLayout form = new FormLayout(feedCount, poolNumber);
        form.setWidthFull();
        form.getStyle().set("margin", "0 auto");
        return form;
    }

    private HorizontalLayout buildButtons() {
        HorizontalLayout buttons = new HorizontalLayout(newBtn, saveBtn, deleteBtn);
        buttons.setWidthFull();
        buttons.setAlignItems(FlexComponent.Alignment.CENTER);
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        buttons.setPadding(false);
        return buttons;
    }

    private void refreshGrid() {
        grid.setItems(service.getAllConfigs());
    }

    private void edit(FeedConfigDto dto) {
        if (dto.getConfig() == null) {
            dto.setConfig(new FeedConfigDetails());
        }
        this.current = dto;
        binder.readBean(current);
        deleteBtn.setEnabled(current.getId() != null);
    }

    private void editNew() {
        FeedConfigDto dto = new FeedConfigDto();
        if (dto.getConfig() == null) {
            dto.setConfig(new FeedConfigDetails());
        }
        edit(dto);
    }
}
