package org.example.FourthOctober.repository.Impl;

import org.example.FourthOctober.config.ManagerConnection;
import org.example.FourthOctober.model.FileInfoEntity;
import org.example.FourthOctober.repository.FileInfoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class FileInfoRepositoryImpl implements FileInfoRepository {
    private final JdbcTemplate jdbcTemplate = ManagerConnection.jdbcTemplate;
    private final FileInfoRowMapper fileInfoRowMapper = new FileInfoRowMapper();
    private static String SQL_FIND_BY_ID = """
            SELECT * FROM user_entity  WHERE id=?
            """;
    private static String SQL_FIND_BY_STORAGE_NAME = """
            SELECT * FROM user_entity WHERE storage_name=? 
    """;
    private static String SQL_SAVE = """
            INSERT INTO user_entity (mime, initial_name, storage_name, length) VALUES (?, ?, ?, ?)
            """;
    private static final String SQL_EXISTS_BY_STORAGE_NAMES = """
            select exists(select 1 from file_info where storage_name = ?)
            """;

    @Override
    public Optional<FileInfoEntity> findById(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, fileInfoRowMapper, id ));
    }

    @Override
    public Optional<FileInfoEntity> findByStorageName(String storageName) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_STORAGE_NAME, fileInfoRowMapper, storageName));
    }

    @Override
    public void save(FileInfoEntity fileInfo) {
        jdbcTemplate.update(
                SQL_SAVE,
                fileInfo.getFileMime(),
                fileInfo.getFileInitialName(),
                fileInfo.getFileStorageName(),
                fileInfo.getFileLength()
        );
    }

    @Override
    public boolean existsByStorageName(String storageName) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(SQL_EXISTS_BY_STORAGE_NAMES,
                Boolean.class, storageName));
    }
    private static final class FileInfoRowMapper implements RowMapper<FileInfoEntity> {
        @Override
        public FileInfoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return FileInfoEntity.builder().
                    fileId(rs.getLong("id"))
                    .fileMime(rs.getString("mime"))
                    .fileInitialName(rs.getString("inital_name"))
                    .fileStorageName(rs.getString("storage_name"))
                    .fileLength(rs.getLong("length"))
                    .build();
        }
    }
}
