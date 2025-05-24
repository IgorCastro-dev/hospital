CREATE TABLE consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    data_hora DATETIME NOT NULL,
    status ENUM('AGENDADA', 'CANCELADA', 'REALIZADA') NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_medico) REFERENCES usuario(id_usuario)
);
