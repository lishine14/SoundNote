package com.soundnote.di;

import com.soundnote.data.local.SoundNoteDatabase;
import com.soundnote.data.local.dao.RecordingDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideRecordingDaoFactory implements Factory<RecordingDao> {
  private final Provider<SoundNoteDatabase> databaseProvider;

  public DatabaseModule_ProvideRecordingDaoFactory(Provider<SoundNoteDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RecordingDao get() {
    return provideRecordingDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRecordingDaoFactory create(
      Provider<SoundNoteDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRecordingDaoFactory(databaseProvider);
  }

  public static RecordingDao provideRecordingDao(SoundNoteDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRecordingDao(database));
  }
}
