package com.soundnote.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.soundnote.data.local.entity.RecordingEntity;
import com.soundnote.data.local.entity.RecordingTagCrossRef;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RecordingDao_Impl implements RecordingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecordingEntity> __insertionAdapterOfRecordingEntity;

  private final EntityInsertionAdapter<RecordingTagCrossRef> __insertionAdapterOfRecordingTagCrossRef;

  private final EntityDeletionOrUpdateAdapter<RecordingEntity> __deletionAdapterOfRecordingEntity;

  private final EntityDeletionOrUpdateAdapter<RecordingTagCrossRef> __deletionAdapterOfRecordingTagCrossRef;

  private final EntityDeletionOrUpdateAdapter<RecordingEntity> __updateAdapterOfRecordingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRecordingById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavorite;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFolder;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTranscription;

  public RecordingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecordingEntity = new EntityInsertionAdapter<RecordingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recordings` (`id`,`fileName`,`filePath`,`format`,`durationMs`,`fileSizeBytes`,`sampleRate`,`bitRate`,`channels`,`isFavorite`,`folderId`,`transcription`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecordingEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getFileName());
        statement.bindString(3, entity.getFilePath());
        statement.bindString(4, entity.getFormat());
        statement.bindLong(5, entity.getDurationMs());
        statement.bindLong(6, entity.getFileSizeBytes());
        statement.bindLong(7, entity.getSampleRate());
        statement.bindLong(8, entity.getBitRate());
        statement.bindLong(9, entity.getChannels());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getFolderId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getFolderId());
        }
        if (entity.getTranscription() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTranscription());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    };
    this.__insertionAdapterOfRecordingTagCrossRef = new EntityInsertionAdapter<RecordingTagCrossRef>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `recording_tags` (`recordingId`,`tagId`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecordingTagCrossRef entity) {
        statement.bindString(1, entity.getRecordingId());
        statement.bindString(2, entity.getTagId());
      }
    };
    this.__deletionAdapterOfRecordingEntity = new EntityDeletionOrUpdateAdapter<RecordingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recordings` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecordingEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__deletionAdapterOfRecordingTagCrossRef = new EntityDeletionOrUpdateAdapter<RecordingTagCrossRef>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recording_tags` WHERE `recordingId` = ? AND `tagId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecordingTagCrossRef entity) {
        statement.bindString(1, entity.getRecordingId());
        statement.bindString(2, entity.getTagId());
      }
    };
    this.__updateAdapterOfRecordingEntity = new EntityDeletionOrUpdateAdapter<RecordingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recordings` SET `id` = ?,`fileName` = ?,`filePath` = ?,`format` = ?,`durationMs` = ?,`fileSizeBytes` = ?,`sampleRate` = ?,`bitRate` = ?,`channels` = ?,`isFavorite` = ?,`folderId` = ?,`transcription` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecordingEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getFileName());
        statement.bindString(3, entity.getFilePath());
        statement.bindString(4, entity.getFormat());
        statement.bindLong(5, entity.getDurationMs());
        statement.bindLong(6, entity.getFileSizeBytes());
        statement.bindLong(7, entity.getSampleRate());
        statement.bindLong(8, entity.getBitRate());
        statement.bindLong(9, entity.getChannels());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getFolderId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getFolderId());
        }
        if (entity.getTranscription() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTranscription());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        statement.bindString(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteRecordingById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recordings WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recordings SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFolder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recordings SET folderId = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTranscription = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recordings SET transcription = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRecording(final RecordingEntity recording,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecordingEntity.insert(recording);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRecordingTag(final RecordingTagCrossRef crossRef,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecordingTagCrossRef.insert(crossRef);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecording(final RecordingEntity recording,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecordingEntity.handle(recording);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecordingTag(final RecordingTagCrossRef crossRef,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecordingTagCrossRef.handle(crossRef);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRecording(final RecordingEntity recording,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRecordingEntity.handle(recording);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecordingById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRecordingById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteRecordingById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavorite(final String id, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFolder(final String id, final String folderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFolder.acquire();
        int _argIndex = 1;
        if (folderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, folderId);
        }
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFolder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTranscription(final String id, final String transcription,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTranscription.acquire();
        int _argIndex = 1;
        if (transcription == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, transcription);
        }
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTranscription.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecordingEntity>> getAllRecordings() {
    final String _sql = "SELECT * FROM recordings ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recordings"}, new Callable<List<RecordingEntity>>() {
      @Override
      @NonNull
      public List<RecordingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final int _cursorIndexOfSampleRate = CursorUtil.getColumnIndexOrThrow(_cursor, "sampleRate");
          final int _cursorIndexOfBitRate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitRate");
          final int _cursorIndexOfChannels = CursorUtil.getColumnIndexOrThrow(_cursor, "channels");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTranscription = CursorUtil.getColumnIndexOrThrow(_cursor, "transcription");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RecordingEntity> _result = new ArrayList<RecordingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecordingEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final int _tmpSampleRate;
            _tmpSampleRate = _cursor.getInt(_cursorIndexOfSampleRate);
            final int _tmpBitRate;
            _tmpBitRate = _cursor.getInt(_cursorIndexOfBitRate);
            final int _tmpChannels;
            _tmpChannels = _cursor.getInt(_cursorIndexOfChannels);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            final String _tmpTranscription;
            if (_cursor.isNull(_cursorIndexOfTranscription)) {
              _tmpTranscription = null;
            } else {
              _tmpTranscription = _cursor.getString(_cursorIndexOfTranscription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecordingEntity(_tmpId,_tmpFileName,_tmpFilePath,_tmpFormat,_tmpDurationMs,_tmpFileSizeBytes,_tmpSampleRate,_tmpBitRate,_tmpChannels,_tmpIsFavorite,_tmpFolderId,_tmpTranscription,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecordingEntity>> getFavoriteRecordings() {
    final String _sql = "SELECT * FROM recordings WHERE isFavorite = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recordings"}, new Callable<List<RecordingEntity>>() {
      @Override
      @NonNull
      public List<RecordingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final int _cursorIndexOfSampleRate = CursorUtil.getColumnIndexOrThrow(_cursor, "sampleRate");
          final int _cursorIndexOfBitRate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitRate");
          final int _cursorIndexOfChannels = CursorUtil.getColumnIndexOrThrow(_cursor, "channels");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTranscription = CursorUtil.getColumnIndexOrThrow(_cursor, "transcription");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RecordingEntity> _result = new ArrayList<RecordingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecordingEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final int _tmpSampleRate;
            _tmpSampleRate = _cursor.getInt(_cursorIndexOfSampleRate);
            final int _tmpBitRate;
            _tmpBitRate = _cursor.getInt(_cursorIndexOfBitRate);
            final int _tmpChannels;
            _tmpChannels = _cursor.getInt(_cursorIndexOfChannels);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            final String _tmpTranscription;
            if (_cursor.isNull(_cursorIndexOfTranscription)) {
              _tmpTranscription = null;
            } else {
              _tmpTranscription = _cursor.getString(_cursorIndexOfTranscription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecordingEntity(_tmpId,_tmpFileName,_tmpFilePath,_tmpFormat,_tmpDurationMs,_tmpFileSizeBytes,_tmpSampleRate,_tmpBitRate,_tmpChannels,_tmpIsFavorite,_tmpFolderId,_tmpTranscription,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRecordingById(final String id,
      final Continuation<? super RecordingEntity> $completion) {
    final String _sql = "SELECT * FROM recordings WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RecordingEntity>() {
      @Override
      @Nullable
      public RecordingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final int _cursorIndexOfSampleRate = CursorUtil.getColumnIndexOrThrow(_cursor, "sampleRate");
          final int _cursorIndexOfBitRate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitRate");
          final int _cursorIndexOfChannels = CursorUtil.getColumnIndexOrThrow(_cursor, "channels");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTranscription = CursorUtil.getColumnIndexOrThrow(_cursor, "transcription");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final RecordingEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final int _tmpSampleRate;
            _tmpSampleRate = _cursor.getInt(_cursorIndexOfSampleRate);
            final int _tmpBitRate;
            _tmpBitRate = _cursor.getInt(_cursorIndexOfBitRate);
            final int _tmpChannels;
            _tmpChannels = _cursor.getInt(_cursorIndexOfChannels);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            final String _tmpTranscription;
            if (_cursor.isNull(_cursorIndexOfTranscription)) {
              _tmpTranscription = null;
            } else {
              _tmpTranscription = _cursor.getString(_cursorIndexOfTranscription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new RecordingEntity(_tmpId,_tmpFileName,_tmpFilePath,_tmpFormat,_tmpDurationMs,_tmpFileSizeBytes,_tmpSampleRate,_tmpBitRate,_tmpChannels,_tmpIsFavorite,_tmpFolderId,_tmpTranscription,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<RecordingEntity>> searchRecordings(final String query) {
    final String _sql = "SELECT * FROM recordings WHERE fileName LIKE '%' || ? || '%' ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recordings"}, new Callable<List<RecordingEntity>>() {
      @Override
      @NonNull
      public List<RecordingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final int _cursorIndexOfSampleRate = CursorUtil.getColumnIndexOrThrow(_cursor, "sampleRate");
          final int _cursorIndexOfBitRate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitRate");
          final int _cursorIndexOfChannels = CursorUtil.getColumnIndexOrThrow(_cursor, "channels");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTranscription = CursorUtil.getColumnIndexOrThrow(_cursor, "transcription");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RecordingEntity> _result = new ArrayList<RecordingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecordingEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final int _tmpSampleRate;
            _tmpSampleRate = _cursor.getInt(_cursorIndexOfSampleRate);
            final int _tmpBitRate;
            _tmpBitRate = _cursor.getInt(_cursorIndexOfBitRate);
            final int _tmpChannels;
            _tmpChannels = _cursor.getInt(_cursorIndexOfChannels);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            final String _tmpTranscription;
            if (_cursor.isNull(_cursorIndexOfTranscription)) {
              _tmpTranscription = null;
            } else {
              _tmpTranscription = _cursor.getString(_cursorIndexOfTranscription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecordingEntity(_tmpId,_tmpFileName,_tmpFilePath,_tmpFormat,_tmpDurationMs,_tmpFileSizeBytes,_tmpSampleRate,_tmpBitRate,_tmpChannels,_tmpIsFavorite,_tmpFolderId,_tmpTranscription,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RecordingEntity>> getRecordingsByFolder(final String folderId) {
    final String _sql = "SELECT * FROM recordings WHERE folderId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, folderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recordings"}, new Callable<List<RecordingEntity>>() {
      @Override
      @NonNull
      public List<RecordingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSizeBytes");
          final int _cursorIndexOfSampleRate = CursorUtil.getColumnIndexOrThrow(_cursor, "sampleRate");
          final int _cursorIndexOfBitRate = CursorUtil.getColumnIndexOrThrow(_cursor, "bitRate");
          final int _cursorIndexOfChannels = CursorUtil.getColumnIndexOrThrow(_cursor, "channels");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folderId");
          final int _cursorIndexOfTranscription = CursorUtil.getColumnIndexOrThrow(_cursor, "transcription");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<RecordingEntity> _result = new ArrayList<RecordingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecordingEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final int _tmpSampleRate;
            _tmpSampleRate = _cursor.getInt(_cursorIndexOfSampleRate);
            final int _tmpBitRate;
            _tmpBitRate = _cursor.getInt(_cursorIndexOfBitRate);
            final int _tmpChannels;
            _tmpChannels = _cursor.getInt(_cursorIndexOfChannels);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFolderId;
            if (_cursor.isNull(_cursorIndexOfFolderId)) {
              _tmpFolderId = null;
            } else {
              _tmpFolderId = _cursor.getString(_cursorIndexOfFolderId);
            }
            final String _tmpTranscription;
            if (_cursor.isNull(_cursorIndexOfTranscription)) {
              _tmpTranscription = null;
            } else {
              _tmpTranscription = _cursor.getString(_cursorIndexOfTranscription);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecordingEntity(_tmpId,_tmpFileName,_tmpFilePath,_tmpFormat,_tmpDurationMs,_tmpFileSizeBytes,_tmpSampleRate,_tmpBitRate,_tmpChannels,_tmpIsFavorite,_tmpFolderId,_tmpTranscription,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<String>> getTagIdsForRecording(final String recordingId) {
    final String _sql = "SELECT tagId FROM recording_tags WHERE recordingId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, recordingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recording_tags"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
