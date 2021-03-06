package com.haeorm.chatchat.root.menu.namechange;

import com.haeorm.chatchat.Client;
import com.haeorm.chatchat.util.Regedit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NameChangeDialog extends Stage{

	VBox vb = new VBox(10);
	
	
	TextField input = new TextField();
	Text title = new Text("변경 할 이름을 입력하세요.");
	Text text = new Text("");
	
	int maxLength = 13;
	
	private Client client = null;
	
	boolean pass = true;
	
	public NameChangeDialog(Client client) {
		super();
		
		this.client = client;
		getIcons().add(client.icon);
		
		vb.getChildren().add(title);
		vb.getChildren().add(input);
		vb.getChildren().add(text);
		
		vb.setAlignment(Pos.CENTER);
		
		title.setFont(Font.font(14));
		title.setTextAlignment(TextAlignment.CENTER);
		
		text.setTextAlignment(TextAlignment.CENTER);
		
		setTitle("이름 변경");
		initOwner(client.getRootStage());
		initModality(Modality.WINDOW_MODAL);
		
		
		
		
		input.setText(Regedit.getStringValue("title"));
		
		setWidth(240);
		setHeight(120);
		setResizable(false);
		
		double centerXPosition = client.getRootStage().getX() + client.getRootStage().getWidth()/2d;
        double centerYPosition = client.getRootStage().getY() + client.getRootStage().getHeight()/2d;
        
        setX(centerXPosition - getWidth()/2d);
        setY(centerYPosition - getHeight()/2d);
		
		Scene scene = new Scene(vb);
		setScene(scene);
		
		setOnCloseRequest(Event -> {
			close();
		});
		
		
		input.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> obersvable, String oldValue, String newValue) {
				if(client.getData().getBlockNameList().contains(newValue.toLowerCase())){
					text.setFill(Color.RED);
					text.setText("사용 할 수 없는 이름입니다.");
					pass = false;
				}else{
					text.setText("");
					pass = true;
				}
			}
		});
		
		input.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent arg0) {
				
				if(arg0.getCode() == KeyCode.ENTER){
					if(input.getText().equals("")){
						text.setFill(Color.RED);
						text.setText("변경할 이름을 입력하세요.");
					}else{
						
						if(!input.getText().equals("") && pass){
							client.getManager().sendRequestChangeName(input.getText());
							close();
						}else{
							input.setText("");
						}
							
						
						
					}
					
					
				}
				
			};
		});
		
	}
	
}
