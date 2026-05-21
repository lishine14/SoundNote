package com.soundnote.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.soundnote.data.local.dao.FolderDao;
import com.soundnote.data.local.dao.FolderDao_Impl;
import com.soundnote.data.local.dao.MarkerDao;
import com.soundnote.data.local.dao.MarkerDao_Impl;
import com.soundnote.data.local.dao.RecordingDao;
import com.soundnote.data.local.dao.RecordingDao_Impl;
import com.soundnote.data.local.dao.TagDao;
import com.soundnote.data.local.dao.TagDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SoundNoteDatabase_Impl extends SoundNoteDatabase {
  private volatile RecordingDao _recordingDao;

  private volatile TagDao _tagDao;

  private volatile FolderDao _folderDao;

  private volatile MarkerDao _markerDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `recordings` (`id` TEXT NOT NULL, `fileName` TEXT NOT NULL, `filePath` TEXT NOT NULL, `format` TEXT NOT NULL, `durationMs` INTEGER NOT NULL, `fileSizeBytes` INTEGER NOT NULL, `sampleRate` INTEGER NOT NULL, `bitRate` INTEGER NOT NULL, `channels` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `folderId` TEXT, `transcription` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tags` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `color` TEXT NOT NULL, `sortOrder` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `folders` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `parentId` TEXT, `isSystem` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `recording_tags` (`recordingId` TEXT NOT NULL, `tagId` TEXT NOT NULL, PRIMARY KEY(`recordingId`, `tagId`), FOREIGN KEY(`recordingId`) REFERENCES `recordings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tagId`) REFERENCES `tags`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_recording_tags_tagId` ON `recording_tags` (`tagId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `markers` (`id` TEXT NOT NULL, `recordingId` TEXT NOT NULL, `timestampMs` INTEGER NOT NULL, `note` TEXT, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`recordingId`) REFERENCES `recordings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_markers_recordingId` ON `markers` (`recordingId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '973cdb8d98a2392c354d5aef7f8460c8')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `recordings`");
        db.execSQL("DROP TABLE IF EXISTS `tags`");
        db.execSQL("DROP TABLE IF EXISTS `folders`");
        db.execSQL("DROP TABLE IF EXISTS `recording_tags`");
        db.execSQL("DROP TABLE IF EXISTS `markers`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsRecordings = new HashMap<String, TableInfo.Column>(14);
        _columnsRecordings.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("fileName", new TableInfo.Column("fileName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("format", new TableInfo.Column("format", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("fileSizeBytes", new TableInfo.Column("fileSizeBytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("sampleRate", new TableInfo.Column("sampleRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("bitRate", new TableInfo.Column("bitRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("channels", new TableInfo.Column("channels", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("folderId", new TableInfo.Column("folderId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("transcription", new TableInfo.Column("transcription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordings.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecordings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecordings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecordings = new TableInfo("recordings", _columnsRecordings, _foreignKeysRecordings, _indicesRecordings);
        final TableInfo _existingRecordings = TableInfo.read(db, "recordings");
        if (!_infoRecordings.equals(_existingRecordings)) {
          return new RoomOpenHelper.ValidationResult(false, "recordings(com.soundnote.data.local.entity.RecordingEntity).\n"
                  + " Expected:\n" + _infoRecordings + "\n"
                  + " Found:\n" + _existingRecordings);
        }
        final HashMap<String, TableInfo.Column> _columnsTags = new HashMap<String, TableInfo.Column>(5);
        _columnsTags.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTags.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTags = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTags = new TableInfo("tags", _columnsTags, _foreignKeysTags, _indicesTags);
        final TableInfo _existingTags = TableInfo.read(db, "tags");
        if (!_infoTags.equals(_existingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "tags(com.soundnote.data.local.entity.TagEntity).\n"
                  + " Expected:\n" + _infoTags + "\n"
                  + " Found:\n" + _existingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsFolders = new HashMap<String, TableInfo.Column>(6);
        _columnsFolders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("parentId", new TableInfo.Column("parentId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("isSystem", new TableInfo.Column("isSystem", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolders.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFolders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFolders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFolders = new TableInfo("folders", _columnsFolders, _foreignKeysFolders, _indicesFolders);
        final TableInfo _existingFolders = TableInfo.read(db, "folders");
        if (!_infoFolders.equals(_existingFolders)) {
          return new RoomOpenHelper.ValidationResult(false, "folders(com.soundnote.data.local.entity.FolderEntity).\n"
                  + " Expected:\n" + _infoFolders + "\n"
                  + " Found:\n" + _existingFolders);
        }
        final HashMap<String, TableInfo.Column> _columnsRecordingTags = new HashMap<String, TableInfo.Column>(2);
        _columnsRecordingTags.put("recordingId", new TableInfo.Column("recordingId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecordingTags.put("tagId", new TableInfo.Column("tagId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecordingTags = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysRecordingTags.add(new TableInfo.ForeignKey("recordings", "CASCADE", "NO ACTION", Arrays.asList("recordingId"), Arrays.asList("id")));
        _foreignKeysRecordingTags.add(new TableInfo.ForeignKey("tags", "CASCADE", "NO ACTION", Arrays.asList("tagId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRecordingTags = new HashSet<TableInfo.Index>(1);
        _indicesRecordingTags.add(new TableInfo.Index("index_recording_tags_tagId", false, Arrays.asList("tagId"), Arrays.asList("ASC")));
        final TableInfo _infoRecordingTags = new TableInfo("recording_tags", _columnsRecordingTags, _foreignKeysRecordingTags, _indicesRecordingTags);
        final TableInfo _existingRecordingTags = TableInfo.read(db, "recording_tags");
        if (!_infoRecordingTags.equals(_existingRecordingTags)) {
          return new RoomOpenHelper.ValidationResult(false, "recording_tags(com.soundnote.data.local.entity.RecordingTagCrossRef).\n"
                  + " Expected:\n" + _infoRecordingTags + "\n"
                  + " Found:\n" + _existingRecordingTags);
        }
        final HashMap<String, TableInfo.Column> _columnsMarkers = new HashMap<String, TableInfo.Column>(5);
        _columnsMarkers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMarkers.put("recordingId", new TableInfo.Column("recordingId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMarkers.put("timestampMs", new TableInfo.Column("timestampMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMarkers.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMarkers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMarkers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMarkers.add(new TableInfo.ForeignKey("recordings", "CASCADE", "NO ACTION", Arrays.asList("recordingId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMarkers = new HashSet<TableInfo.Index>(1);
        _indicesMarkers.add(new TableInfo.Index("index_markers_recordingId", false, Arrays.asList("recordingId"), Arrays.asList("ASC")));
        final TableInfo _infoMarkers = new TableInfo("markers", _columnsMarkers, _foreignKeysMarkers, _indicesMarkers);
        final TableInfo _existingMarkers = TableInfo.read(db, "markers");
        if (!_infoMarkers.equals(_existingMarkers)) {
          return new RoomOpenHelper.ValidationResult(false, "markers(com.soundnote.data.local.entity.MarkerEntity).\n"
                  + " Expected:\n" + _infoMarkers + "\n"
                  + " Found:\n" + _existingMarkers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "973cdb8d98a2392c354d5aef7f8460c8", "8414321f706de6c333cbebf43f4ed83a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "recordings","tags","folders","recording_tags","markers");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `recordings`");
      _db.execSQL("DELETE FROM `tags`");
      _db.execSQL("DELETE FROM `folders`");
      _db.execSQL("DELETE FROM `recording_tags`");
      _db.execSQL("DELETE FROM `markers`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(RecordingDao.class, RecordingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TagDao.class, TagDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FolderDao.class, FolderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MarkerDao.class, MarkerDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public RecordingDao recordingDao() {
    if (_recordingDao != null) {
      return _recordingDao;
    } else {
      synchronized(this) {
        if(_recordingDao == null) {
          _recordingDao = new RecordingDao_Impl(this);
        }
        return _recordingDao;
      }
    }
  }

  @Override
  public TagDao tagDao() {
    if (_tagDao != null) {
      return _tagDao;
    } else {
      synchronized(this) {
        if(_tagDao == null) {
          _tagDao = new TagDao_Impl(this);
        }
        return _tagDao;
      }
    }
  }

  @Override
  public FolderDao folderDao() {
    if (_folderDao != null) {
      return _folderDao;
    } else {
      synchronized(this) {
        if(_folderDao == null) {
          _folderDao = new FolderDao_Impl(this);
        }
        return _folderDao;
      }
    }
  }

  @Override
  public MarkerDao markerDao() {
    if (_markerDao != null) {
      return _markerDao;
    } else {
      synchronized(this) {
        if(_markerDao == null) {
          _markerDao = new MarkerDao_Impl(this);
        }
        return _markerDao;
      }
    }
  }
}
