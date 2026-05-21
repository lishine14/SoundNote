package com.soundnote.di;

import com.soundnote.data.local.SoundNoteDatabase;
import com.soundnote.data.local.dao.MarkerDao;
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
public final class DatabaseModule_ProvideMarkerDaoFactory implements Factory<MarkerDao> {
  private final Provider<SoundNoteDatabase> databaseProvider;

  public DatabaseModule_ProvideMarkerDaoFactory(Provider<SoundNoteDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MarkerDao get() {
    return provideMarkerDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideMarkerDaoFactory create(
      Provider<SoundNoteDatabase> databaseProvider) {
    return new DatabaseModule_ProvideMarkerDaoFactory(databaseProvider);
  }

  public static MarkerDao provideMarkerDao(SoundNoteDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMarkerDao(database));
  }
}
