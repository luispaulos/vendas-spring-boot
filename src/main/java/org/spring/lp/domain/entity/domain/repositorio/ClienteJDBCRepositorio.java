package org.spring.lp.domain.entity.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteJDBCRepositorio {

    private static final String INSERT = "insert into cliente(nome) values (?)";
    private static final String SELECT_ALL = "select * from cliente";
    private static final String DELETE = "delete from cliente where id = ?";
    private static final String UPDATE = "update cliente set nome = ? where id = ?";
    private static final String SELECT_POR_NOME = "select * from cliente where nome like ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public List<Cliente> obterTodos() {
        return jdbcTemplate.query(SELECT_ALL, getClieteMapper());
    }

    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(SELECT_POR_NOME, new Object[]{"%" + nome + "%"}, getClieteMapper());
    }

    private RowMapper<Cliente> getClieteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        };
    }

    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }

    public void deletar(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }
}
