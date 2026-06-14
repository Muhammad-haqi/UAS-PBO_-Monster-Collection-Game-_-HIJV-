module com.game.projekgame {
    requires javafx.controls;
    requires javafx.fxml;

    // Dua baris ini wajib ditambahkan agar tidak merah!
    requires java.net.http;
    requires com.google.gson;

    opens com.game.projekgame to javafx.fxml;
    opens com.game.projekgame.controller to javafx.fxml;
    // Buka akses untuk model agar bisa dibaca oleh tabel/library
    opens com.game.projekgame.model to javafx.fxml, com.google.gson;

    exports com.game.projekgame;
    exports com.game.projekgame.controller;
    exports com.game.projekgame.model;
}