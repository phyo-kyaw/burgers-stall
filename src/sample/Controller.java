package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.apache.commons.lang3.exception.ExceptionUtils;
import sample.DecimalTextFormatter;
import sample.OpService;
import sample.model.CheeseAddOn;
import sample.model.Item;
import sample.model.OrderItem;
import sample.model.SaleOrder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static javafx.geometry.Pos.CENTER_RIGHT;

public class Controller {

    @FXML
    private CheckBox cb_chips;

    @FXML
    private CheckBox cb_coke;

    @FXML
    private CheckBox cb_hamburger;

    @FXML
    private RadioButton rb_cheese;

    @FXML
    private TextField tf_chips;

    @FXML
    private TextField tf_coke;

    @FXML
    private TextField tf_hamburger;

    @FXML
    private TextField tf_changes;

    @FXML
    private TextField tf_paid;

    @FXML
    private TextField tf_total;

    @FXML
    private ChoiceBox<Integer> dl_chips;

    @FXML
    private ChoiceBox<Integer> dl_coke;

    @FXML
    private ChoiceBox<Integer> dl_hamburger;

    final DecimalFormat df = new DecimalFormat("0.00");

    OpService opService = OpService.getInstance();

    Item[] itemArray = {null, null, null, null, null, null};
    List<OrderItem> orderItemList ;
    Integer[] quantity = {null, null, null, null, null, null};
    SaleOrder saleOrder;

    @FXML
    public void initialize() {

        List<Integer> ret = IntStream.rangeClosed(0, 20).boxed().collect(Collectors.toList());
        dl_chips.getItems().addAll(ret);
        dl_coke.getItems().addAll(ret);
        dl_hamburger.getItems().addAll(ret);

        tf_chips.setAlignment(CENTER_RIGHT);
        tf_coke.setAlignment(CENTER_RIGHT);
        tf_hamburger.setAlignment(CENTER_RIGHT);

        tf_total.setAlignment(CENTER_RIGHT);
        tf_changes.setAlignment(CENTER_RIGHT);
        tf_paid.setAlignment(CENTER_RIGHT);

        tf_chips.setEditable(false);
        tf_coke.setEditable(false);
        tf_hamburger.setEditable(false);

        tf_total.setEditable(false);
        tf_changes.setEditable(false);
        //tf_paid.setAlignment(CENTER_RIGHT);

        updateUiChanges();
    }

    @FXML
    void CheckAction(ActionEvent event) {

        updateUiChanges();
    }

    @FXML
    void ChangeAction(Event event) {

        updateUiChanges();
    }

    @FXML
    void ChangeAction_Total(Event event) {

        updateUiChanges();
    }

    @FXML
    void PayAction(MouseEvent event)  {

        Alert a = new Alert(AlertType.NONE);

        if (Double.parseDouble(tf_paid.getText()) >= 0 && Double.parseDouble(tf_total.getText()) > 0 ) {
            if (Double.parseDouble(tf_paid.getText()) >= Double.parseDouble(tf_total.getText())) {
                tf_changes.setText(df.format(Double.parseDouble(tf_paid.getText()) - Double.parseDouble(tf_total.getText())));

                itemArray[4] = opService.getItemList()
                        .parallelStream()
                        .filter(x -> x.getName().equalsIgnoreCase("Changes"))
                        .findFirst()
                        .get();

                itemArray[5] = opService.getItemList()
                        .parallelStream()
                        .filter(x -> x.getName().equalsIgnoreCase("Paid"))
                        .findFirst()
                        .get();

                saleOrder = new SaleOrder(LocalDateTime.now());
                orderItemList = new ArrayList<OrderItem>();

                for( int i =0; i < 3; i++ ){
                    if(!isNull(itemArray[i])){
                        //orderItemList.add(new OrderItem(saleOrder, itemArray[i], quantity[i], itemArray[i].getPrice() * quantity[i] ) );
                        orderItemList.add(new OrderItem(itemArray[i], quantity[i], itemArray[i].getPrice() * quantity[i] ) );
                    }

                }
                orderItemList.add(new OrderItem( itemArray[3], quantity[3], Double.parseDouble(tf_total.getText()) ) );
                orderItemList.add(new OrderItem(itemArray[4], quantity[4], Double.parseDouble(tf_changes.getText()) ) );
                orderItemList.add(new OrderItem(itemArray[5], quantity[5], Double.parseDouble(tf_paid.getText()) ) );

                saleOrder.setOrderItems(orderItemList);

                //opService.getSaleOrderList().add(saleOrder);

                ObjectMapper mapper = new ObjectMapper();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                mapper.setDateFormat(dateFormat);


                String jsonStr = null;

                try {
                    jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(saleOrder);

                    showAlert(AlertType.CONFIRMATION, "Sale Order", jsonStr);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();

                    String stacktrace = ExceptionUtils.getStackTrace(e);
                    a.setAlertType(AlertType.ERROR);
                    a.setContentText(stacktrace);
                    a.show();

                }



                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(e -> {
                    tf_changes.setText(null);
                    tf_paid.setText(null);

                    cb_chips.setSelected(false);
                    cb_coke.setSelected(false);
                    cb_hamburger.setSelected(false);

                    updateUiChanges();
                });
                pause.play();

            } else {
                a.setAlertType(AlertType.ERROR);
                a.setContentText("Amount paid must be larger than total!");
                a.show();
            }
        }
    }

    void updateUiChanges() {

        Double price, amount, payAmount, total = 0.0;



        tf_paid.setTextFormatter(new DecimalTextFormatter(2, 2));

        if (cb_hamburger.isSelected()) {

            if (rb_cheese.isSelected()) {
                itemArray[0] = new CheeseAddOn( opService.getItemList()
                        .parallelStream()
                        .filter(x -> x.getName().equalsIgnoreCase("Hamburger"))
                        .findFirst()
                        .get() );
            }
            else{
                itemArray[0] = opService.getItemList()
                        .parallelStream()
                        .filter(x -> x.getName().equalsIgnoreCase("Hamburger"))
                        .findFirst()
                        .get();
            }

            if (dl_hamburger.getValue() == null) {
                dl_hamburger.setValue(1);
            }

            quantity[0] = dl_hamburger.getValue();
            amount = itemArray[0].getPrice() * quantity[0];
            total += amount;

            tf_hamburger.setText(df.format(amount));
        } else {
            dl_hamburger.setValue(null);
            tf_hamburger.setText(null);
            itemArray[0] = null;
            quantity[0] = null;
        }


        if (cb_chips.isSelected()) {
            itemArray[1] = opService.getItemList()
                    .parallelStream()
                    .filter(x -> x.getName().equalsIgnoreCase("Chips"))
                    .findFirst()
                    .get();

            if (dl_chips.getValue() == null) {
                dl_chips.setValue(1);
            }
            quantity[1] = dl_chips.getValue();
            amount = itemArray[1].getPrice() * quantity[1];
            total += amount;

            tf_chips.setText(df.format(amount));


        } else {
            dl_chips.setValue(null);
            tf_chips.setText(null);
            itemArray[1] = null;
            quantity[1] = null;
        }

        if (cb_coke.isSelected()) {
            itemArray[2] = opService.getItemList()
                    .parallelStream()
                    .filter(x -> x.getName().equalsIgnoreCase("Coke"))
                    .findFirst()
                    .get();

            if (dl_coke.getValue() == null) {
                dl_coke.setValue(1);
            }
            quantity[2] = dl_coke.getValue();
            amount = itemArray[2].getPrice() * quantity[2];
            total += amount;

            tf_coke.setText(df.format(amount));
        } else {
            dl_coke.setValue(null);
            tf_coke.setText(null);
            itemArray[2] = null;
            quantity[2] = null;
        }




        if (total >= 0) {
            tf_total.setText(df.format(total));
            itemArray[3] = opService.getItemList()
                    .parallelStream()
                    .filter(x -> x.getName().equalsIgnoreCase("Total"))
                    .findFirst()
                    .get();
        }

    }

    private Optional<ButtonType> showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        TextArea area = new TextArea(content);
        area.setWrapText(true);
        area.setEditable(false);

        alert.getDialogPane().setContent(area);
        alert.setResizable(true);

        return alert.showAndWait();
    }
}

