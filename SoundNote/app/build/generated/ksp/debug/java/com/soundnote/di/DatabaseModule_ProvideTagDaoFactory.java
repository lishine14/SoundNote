package com.soundnote.di;

import com.soundnote.data.local.SoundNoteDatabase;
import com.soundnote.data.local.dao.TagDao;
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
public final class DatabaseModule_ProvideTagDaoFactory implements Factory<TagDao> {
  private final Provider<SoundNoteDatabase> databaseProvider;

  public DatabaseModule_ProvideTagDaoFactory(Provider<SoundNoteDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TagDao get() {
    return provideTagDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTagDaoFactory create(
      Provider<SoundNoteDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTagDaoFactory(databaseProvider);
  }

  public static TagDao provideTagDao(SoundNoteDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTagDao(database));
  }
}
