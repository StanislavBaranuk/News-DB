/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import java.net.URL;
import javax.swing.ImageIcon;
import newsdb.*;
//import frames.*;

/**
 *
 * @author stanislaw
 */
public class FrameController {
    
    private Controller controller;
    
    public General gFrame;
    public Setting sFrame;
    public Functions fFrame;
    public AddNewsFrame anFrame;
    public OpenNewsFrame onFrame;
    public SelectOpenNewsFrame soFrame;
    public ComentsViewFrame cvFrame;
    public CategoryFrame cFrame;
    public PartnerNewsFrame pnFrame;
    public OtherInformationFrame oiFrame;
    public OprosFrame oFrame;
    public AutorsFrame saFrame;
    public OpenAutorsFrame oaFrame;
    public SelectOpenAutorsFrame soaFrame;
    public AddCityFrame aciFrame;
    public AddCurrencyFrame acuFrame;
    public AddComentFrame acFrame;
    public AddCategoryFrame acatFrame;

    public FrameController(Controller controller) {
        this.controller = controller;
        this.gFrame = new General(controller);
        this.sFrame = new Setting(controller);
        this.fFrame = new Functions(controller);
        this.anFrame = new AddNewsFrame(controller);
        this.onFrame = new OpenNewsFrame(controller);
        this.soFrame = new SelectOpenNewsFrame(controller);
        this.cvFrame = new ComentsViewFrame(controller);
        this.cFrame = new CategoryFrame(controller);
        this.pnFrame = new PartnerNewsFrame(controller);
        this.oiFrame = new OtherInformationFrame(controller);
        this.oFrame = new OprosFrame(controller);
        this.saFrame = new AutorsFrame(controller);
        this.oaFrame = new OpenAutorsFrame(controller);
        this.soaFrame = new SelectOpenAutorsFrame(controller);
        this.aciFrame = new AddCityFrame(controller);
        this.acuFrame = new AddCurrencyFrame(controller);
        this.acFrame = new AddComentFrame(controller);
        this.acatFrame = new AddCategoryFrame(controller);
    }
    
    public ImageIcon createIcon(String path) {
        URL imgURL = SelectOpenNewsFrame.class.getResource(path);     
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + path);
            return null;
        }
    }
    
    public void show_add_category_frame() {
        acatFrame.setVisible(true);
    }
    
    public void hide_add_category_frame() {
        acatFrame.setVisible(false);
    }
    
    public void show_general_frame() {
        gFrame.setVisible(true);
    }
       
    public void show_opros_frame() {
        oFrame.setVisible(true);
    }
    
    public void hide_opros_frame() {
        oFrame.setVisible(false);
    }
    
    public void show_other_information_frame() {
        gFrame.setVisible(false);
        oiFrame.setVisible(true);
    }
    
    public void hide_other_information_frame() {
        gFrame.setVisible(true);
        oiFrame.setVisible(false);
    }
    
    public void show_partner_news_frame() {
        pnFrame.setVisible(true);
    }
    
    public void hide_cpartner_news_frame() {
        pnFrame.setVisible(false);
    }
    
    public void show_category_frame() {
        gFrame.setVisible(false);
        cFrame.setVisible(true);
    }
    
    public void hide_category_frame() {
        gFrame.setVisible(true);
        cFrame.setVisible(false);
    }
    
    public void show_setting_frame() {
        gFrame.setEnabled(false);
        sFrame.setVisible(true);
    }
    
    public void hide_setting_frame() {
        gFrame.setEnabled(true);
        sFrame.setVisible(false);
    }
    
    public void show_coments_view_frame() {
        cvFrame.setVisible(true);
    }
    
    public void hide_coments_view_frame() {
        cvFrame.setVisible(false);
    }
    
    public void show_add_news_frame() {
        anFrame.setVisible(true);
        gFrame.setVisible(false);
    }
    
    public void hide_add_news_frame() {
        anFrame.setVisible(false);
        gFrame.setVisible(true);
    }
    
    public void show_open_news_frame() {
        onFrame.setVisible(true);
        gFrame.setVisible(false);
    }
    
    public void hide_open_news_frame() {
        onFrame.setVisible(false);
        gFrame.setVisible(true);
    }
    
    public void show_select_news_frame() {
        soFrame.setVisible(true);
    }
    
    public void show_select_autors_frame() {
        soaFrame.setVisible(true);
    }
    
    public void show_autor_frame() {
        saFrame.setVisible(true);
        gFrame.setVisible(false);
    }
    
    public void hide_autor_frame() {
        saFrame.setVisible(false);
        gFrame.setVisible(true);
    }
    
    public void show_open_autors_frame() {
        oaFrame.setVisible(true);
        gFrame.setVisible(false);
    }
    
    public void hide_open_autors_frame() {
        oaFrame.setVisible(false);
        gFrame.setVisible(true);
    }
    
    public void show_add_city_frame() {
        aciFrame.setVisible(true);
        oiFrame.setVisible(false);
    }
    
    public void hide_add_city_frame() {
        aciFrame.setVisible(false);
        oiFrame.setVisible(true);
    }
    
    public void show_add_currency_frame() {
        acuFrame.setVisible(true);
        oiFrame.setVisible(false);
    }
    
    public void hide_add_currency_frame() {
        acuFrame.setVisible(false);
        oiFrame.setVisible(true);
    }

    public void show_add_coments_frame() {
        acFrame.setVisible(true);
    }
    
    public void hide_add_coments_frame() {
        acFrame.setVisible(false);
    }

}
