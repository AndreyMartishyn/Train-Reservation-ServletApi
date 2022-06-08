package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.utils.db_pool.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDaoImpl implements TicketDao {
    private static final Logger log = LogManager.getLogger(TicketDaoImpl.class);
    private static final String CREATE_TICKET = "INSERT INTO tickets (id, user_id, train_id, first_name, last_name,  " +
            "departure_station, departure_time, arrival_station, arrival_time, place, wagon_id, price, isPaid, duration) " +
            "VALUES (?, ?, ?, ?, ?, ? , ?, ?, ? , ?, ? , ?, ?, ?);";
    private static final String GET_TICKETS_BY_USER_ID = "SELECT * FROM tickets WHERE user_id = ?;";
    private static final String GET_ALL_TICKETS = "SELECT * FROM tickets;";
    private static final String UPDATE_TICKET_PAID_STATUS = "UPDATE tickets SET isPaid = ? WHERE id = ?;";

    @Override
    public boolean createTicket(Ticket ticket) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TICKET)) {
            createTicketStatement(preparedStatement, ticket);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            log.error("Problems when creating ticket {}", e.toString());
            return false;
        }
        return false;
    }

    @Override
    public Optional<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TICKETS)) {
            ResultSet ticketFromResultSet = preparedStatement.executeQuery();
            while (ticketFromResultSet.next()) {
                tickets.add(getTicketFromResultSet(ticketFromResultSet));
            }
        } catch (SQLException e) {
            log.error("Problems with getting all tickets {}", e.toString());
        }
        return Optional.of(tickets);
    }

    @Override
    public Optional<List<Ticket>> getAllTicketsById(int id) {
        List<Ticket> ticketsFromDb = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TICKETS_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ticketsFromDb.add(getTicketFromResultSet(resultSet));
            }
        } catch (SQLException exception) {
            log.error("Problems with getting list of tickets for user {}", exception.toString());
        }
        return Optional.of(ticketsFromDb);
    }

    @Override
    public boolean updateTicketToPaid(int ticketId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_TICKET_PAID_STATUS);
            connection.setAutoCommit(false);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Problems with updating ticket {}", e.toString());
            if (connection != null) {
                try {
                    connection.rollback();
                    return false;
                } catch (SQLException exception) {
                    log.error("Problems with transaction {}", e.toString());
                }
            }
        } finally {
            close(connection);
            close(preparedStatement);
        }
        return true;
    }

    private void createTicketStatement(PreparedStatement preparedStatement, Ticket ticket) throws SQLException {
        preparedStatement.setInt(1, ticket.getId());
        preparedStatement.setInt(2, ticket.getUserId());
        preparedStatement.setInt(3, ticket.getTrain().getId());
        preparedStatement.setString(4, ticket.getFirstName());
        preparedStatement.setString(5, ticket.getLastName());
        preparedStatement.setInt(6, ticket.getDepartureStation().getId());
        preparedStatement.setString(7, ticket.getDepartureTime());
        preparedStatement.setInt(8, ticket.getArrivalStation().getId());
        preparedStatement.setString(9, ticket.getArrivalTime());
        preparedStatement.setInt(10, ticket.getPlace());
        preparedStatement.setInt(11, ticket.getWagon().getId());
        preparedStatement.setInt(12, ticket.getPrice());
        preparedStatement.setBoolean(13, ticket.isPaid());
        preparedStatement.setString(14, ticket.getDuration());
    }

    private Ticket getTicketFromResultSet(ResultSet resultSet) throws SQLException {
        TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
        StationDao stationDao = new StationDaoImpl();
        Optional<Train> train = trainAndModelDao.getTrain(resultSet.getInt(3));
        Ticket ticketFromDb = new Ticket();
        Optional<Station> departureStation = stationDao.getById(resultSet.getInt(6));
        Optional<Station> arrivalStation = stationDao.getById(resultSet.getInt(8));
        Optional<Wagon> wagonFromDb = trainAndModelDao.getWagonById(resultSet.getInt(11));
        ticketFromDb.setId(resultSet.getInt(1));
        ticketFromDb.setUserId(resultSet.getInt(2));
        train.ifPresent(ticketFromDb::setTrain);
        ticketFromDb.setFirstName(resultSet.getString(4));
        ticketFromDb.setLastName(resultSet.getString(5));
        departureStation.ifPresent(ticketFromDb::setDepartureStation);
        ticketFromDb.setDepartureTime(resultSet.getString(7));
        arrivalStation.ifPresent(ticketFromDb::setArrivalStation);
        ticketFromDb.setArrivalTime(resultSet.getString(9));
        ticketFromDb.setPlace(resultSet.getInt(10));
        wagonFromDb.ifPresent(ticketFromDb::setWagon);
        ticketFromDb.setType(wagonFromDb.get().getType());
        ticketFromDb.setPrice(resultSet.getInt(12));
        ticketFromDb.setPaid(resultSet.getBoolean(13));
        ticketFromDb.setDuration(resultSet.getString(14));
        return ticketFromDb;
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.error("Failed closing resource {}", e.toString());
            }
        }
    }
}
