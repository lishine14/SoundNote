package com.soundnote.data.repository;

import com.soundnote.data.local.dao.MarkerDao;
import com.soundnote.data.local.dao.RecordingDao;
import com.soundnote.data.local.dao.TagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RecordingRepositoryImpl_Factory implements Factory<RecordingRepositoryImpl> {
  private final Provider<RecordingDao> recordingDaoProvider;

  private final Provider<TagDao> tagDaoProvider;

  private final Provider<MarkerDao> markerDaoProvider;

  public RecordingRepositoryImpl_Factory(Provider<RecordingDao> recordingDaoProvider,
      Provider<TagDao> tagDaoProvider, Provider<MarkerDao> markerDaoProvider) {
    this.recordingDaoProvider = recordingDaoProvider;
    this.tagDaoProvider = tagDaoProvider;
    this.markerDaoProvider = markerDaoProvider;
  }

  @Override
  public RecordingRepositoryImpl get() {
    return newInstance(recordingDaoProvider.get(), tagDaoProvider.get(), markerDaoProvider.get());
  }

  public static RecordingRepositoryImpl_Factory create(Provider<RecordingDao> recordingDaoProvider,
      Provider<TagDao> tagDaoProvider, Provider<MarkerDao> markerDaoProvider) {
    return new RecordingRepositoryImpl_Factory(recordingDaoProvider, tagDaoProvider, markerDaoProvider);
  }

  public static RecordingRepositoryImpl newInstance(RecordingDao recordingDao, TagDao tagDao,
      MarkerDao markerDao) {
    return new RecordingRepositoryImpl(recordingDao, tagDao, markerDao);
  }
}
