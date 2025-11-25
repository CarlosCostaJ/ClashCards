package pc;

import classes.Carta;
import data.CartaDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class TelaCadastroCarta {

    private VBox root;
    private CartaDAO dao;
    private String pastaProjeto;

    private List<Carta> listaEmMemoria;
    private Carta selecionada = null;

    public TelaCadastroCarta(String pastaProjeto, List<Carta> listaEmMemoria, CartaDAO dao) {
        this.pastaProjeto = pastaProjeto;
        this.listaEmMemoria = listaEmMemoria;
        this.dao = dao;
        criarLayout();
    }

    public VBox getLayout() { return root; }

    private void criarLayout() {
        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);

        TextField txtNome = new TextField();
        TextField txtNivel = new TextField();
        TextField txtElixir = new TextField();
        ComboBox<Carta.Tipo> cbTipo = new ComboBox<>();
        cbTipo.getItems().setAll(Carta.Tipo.values());
        ComboBox<Carta.Raridade> cbRar = new ComboBox<>();
        cbRar.getItems().setAll(Carta.Raridade.values());
        TextField txtDano = new TextField();
        TextField txtDps = new TextField();
        TextField txtVida = new TextField();
        TextField txtAlvos = new TextField();
        TextField txtAlcance = new TextField();
        TextField txtVelocidade = new TextField();
        TextField txtVelImpacto = new TextField();
        TextField txtImagem = new TextField();
        txtImagem.setEditable(false);

        ImageView preview = new ImageView();
        preview.setFitWidth(120);
        preview.setFitHeight(160);
        preview.setPreserveRatio(true);

        Button btnEscolherImg = new Button("Escolher imagem");
        btnEscolherImg.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Escolher imagem");
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File sel = fc.showOpenDialog(null);
            if (sel != null) {
                try {

                    File dirImgs = new File(pastaProjeto, "imagens");
                    if (!dirImgs.exists()) dirImgs.mkdirs();
                    File dest = new File(dirImgs, sel.getName());
                    Files.copy(sel.toPath(), dest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    String relativo = "imagens/" + sel.getName();
                    txtImagem.setText(relativo);
                    preview.setImage(new Image(new FileInputStream(dest)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btnSalvar = new Button("Salvar");
        Button btnEditar = new Button("Editar");
        Button btnExcluir = new Button("Excluir");
        Button btnLimpar = new Button("Limpar");

        int r = 0;
        grid.add(new Label("Nome:"), 0, r); grid.add(txtNome, 1, r++);
        grid.add(new Label("Nível:"), 0, r); grid.add(txtNivel, 1, r++);
        grid.add(new Label("Elixir:"), 0, r); grid.add(txtElixir, 1, r++);
        grid.add(new Label("Tipo:"), 0, r); grid.add(cbTipo, 1, r++);
        grid.add(new Label("Raridade:"), 0, r); grid.add(cbRar, 1, r++);
        grid.add(new Label("Dano:"), 0, r); grid.add(txtDano, 1, r++);
        grid.add(new Label("DPS:"), 0, r); grid.add(txtDps, 1, r++);
        grid.add(new Label("Vida:"), 0, r); grid.add(txtVida, 1, r++);
        grid.add(new Label("Alvos:"), 0, r); grid.add(txtAlvos, 1, r++);
        grid.add(new Label("Alcance:"), 0, r); grid.add(txtAlcance, 1, r++);
        grid.add(new Label("Velocidade:"), 0, r); grid.add(txtVelocidade, 1, r++);
        grid.add(new Label("Vel Impacto:"), 0, r); grid.add(txtVelImpacto, 1, r++);
        grid.add(new Label("Imagem:"), 0, r); grid.add(txtImagem, 1, r); grid.add(btnEscolherImg, 2, r++);

        HBox botoes = new HBox(8, btnSalvar, btnEditar, btnExcluir, btnLimpar);
        botoes.setAlignment(Pos.CENTER);

        HBox main = new HBox(12, grid, preview);
        root.getChildren().addAll(new Label("Cadastro de Carta"), main, botoes);

        btnSalvar.setOnAction(ev -> {
            try {
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) throw new IllegalArgumentException("Nome obrigatório");

                if (dao.existePorNome(nome)) {
                    Alert a = new Alert(Alert.AlertType.WARNING, "Já existe carta com esse nome.");
                    a.showAndWait();
                    return;
                }

                Carta c = new Carta(
                        nome,
                        Integer.parseInt(txtNivel.getText()),
                        Integer.parseInt(txtElixir.getText()),
                        cbTipo.getValue(),
                        cbRar.getValue(),
                        txtImagem.getText(),
                        Integer.parseInt(txtDano.getText()),
                        Double.parseDouble(txtDps.getText()),
                        Integer.parseInt(txtVida.getText()),
                        txtAlvos.getText(),
                        Double.parseDouble(txtAlcance.getText()),
                        Double.parseDouble(txtVelocidade.getText()),
                        Double.parseDouble(txtVelImpacto.getText())
                );

                listaEmMemoria.add(c);
                dao.salvarTodas(listaEmMemoria);
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Carta salva!");
                ok.showAndWait();
                limparCampos();

            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Erro salvar: " + ex.getMessage());
                err.showAndWait();
            }
        });

        btnEditar.setOnAction(ev -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Editar carta");

            ChoiceDialog<String> cd = new ChoiceDialog<>();
            for (Carta c : listaEmMemoria) cd.getItems().add(c.getNome());
            Optional<String> res = cd.showAndWait();
            if (res.isPresent()) {
                String nomeSel = res.get();
                Carta cSel = listaEmMemoria.stream().filter(x->x.getNome().equalsIgnoreCase(nomeSel)).findFirst().orElse(null);
                if (cSel != null) {
                    txtNome.setText(cSel.getNome());
                    txtNivel.setText(String.valueOf(cSel.getNivel()));
                    txtElixir.setText(String.valueOf(cSel.getCustoElixir()));
                    cbTipo.setValue(cSel.getTipo());
                    cbRar.setValue(cSel.getRaridade());
                    txtDano.setText(String.valueOf(cSel.getDano()));
                    txtDps.setText(String.valueOf(cSel.getDanoPorSegundo()));
                    txtVida.setText(String.valueOf(cSel.getPontosVida()));
                    txtAlvos.setText(cSel.getAlvos());
                    txtAlcance.setText(String.valueOf(cSel.getAlcance()));
                    txtVelocidade.setText(String.valueOf(cSel.getVelocidade()));
                    txtVelImpacto.setText(String.valueOf(cSel.getVelocidadeImpacto()));
                    txtImagem.setText(cSel.getCaminhoImagem());
                    if (cSel.getCaminhoImagem() != null && !cSel.getCaminhoImagem().isEmpty()) {
                        try { preview.setImage(new Image(new FileInputStream(new File(pastaProjeto, cSel.getCaminhoImagem())))); } catch (Exception ignored) {}
                    }
                    selecionada = cSel;

                    btnSalvar.setDisable(true);
                    Button btnConfirm = new Button("Confirmar Alteração");
                    btnConfirm.setOnAction(ae -> {
                        try {
                            selecionada.setNome(txtNome.getText().trim());
                            selecionada.setNivel(Integer.parseInt(txtNivel.getText()));
                            selecionada.setCustoElixir(Integer.parseInt(txtElixir.getText()));
                            selecionada.setTipo(cbTipo.getValue());
                            selecionada.setRaridade(cbRar.getValue());
                            selecionada.setDano(Integer.parseInt(txtDano.getText()));
                            selecionada.setDanoPorSegundo(Double.parseDouble(txtDps.getText()));
                            selecionada.setPontosVida(Integer.parseInt(txtVida.getText()));
                            selecionada.setAlvos(txtAlvos.getText());
                            selecionada.setAlcance(Double.parseDouble(txtAlcance.getText()));
                            selecionada.setVelocidade(Double.parseDouble(txtVelocidade.getText()));
                            selecionada.setVelocidadeImpacto(Double.parseDouble(txtVelImpacto.getText()));
                            selecionada.setCaminhoImagem(txtImagem.getText());

                            dao.salvarTodas(listaEmMemoria);
                            Alert ok = new Alert(Alert.AlertType.INFORMATION, "Alteração salva!");
                            ok.showAndWait();
                            btnSalvar.setDisable(false);
                            limparCampos();
                        } catch (Exception ex) {
                            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage()).showAndWait();
                        }
                    });
                    root.getChildren().add(btnConfirm);
                }
            }
        });

        btnExcluir.setOnAction(ev -> {
            ChoiceDialog<String> cd = new ChoiceDialog<>();
            for (Carta c : listaEmMemoria) cd.getItems().add(c.getNome());
            Optional<String> res = cd.showAndWait();
            if (res.isPresent()) {
                String nomeSel = res.get();
                listaEmMemoria.removeIf(x -> x.getNome().equalsIgnoreCase(nomeSel));
                dao.salvarTodas(listaEmMemoria);
                new Alert(Alert.AlertType.INFORMATION, "Carta removida").showAndWait();
            }
        });

        btnLimpar.setOnAction(ev -> limparCampos());


        voidClearFields(txtNome, txtNivel, txtElixir, cbTipo, cbRar, txtDano, txtDps, txtVida, txtAlvos, txtAlcance, txtVelocidade, txtVelImpacto, txtImagem, preview);
    }


    private void voidClearFields(TextField txtNome, TextField txtNivel, TextField txtElixir, ComboBox<Carta.Tipo> cbTipo,
                                 ComboBox<Carta.Raridade> cbRar, TextField txtDano, TextField txtDps, TextField txtVida,
                                 TextField txtAlvos, TextField txtAlcance, TextField txtVelocidade, TextField txtVelImpacto,
                                 TextField txtImagem, ImageView preview) {
        txtNome.clear(); txtNivel.clear(); txtElixir.clear(); cbTipo.setValue(null); cbRar.setValue(null);
        txtDano.clear(); txtDps.clear(); txtVida.clear(); txtAlvos.clear(); txtAlcance.clear(); txtVelocidade.clear();
        txtVelImpacto.clear(); txtImagem.clear(); preview.setImage(null);
    }


    private void limparCampos() {

    }
}
