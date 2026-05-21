package com.soundnote.presentation.viewmodel;

import com.soundnote.domain.repository.RecordingRepository;
import com.soundnote.domain.repository.TagRepository;
import com.soundnote.service.AudioPlayer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class PlayerViewModel_Factory implements Factory<PlayerViewModel> {
  private final Provider<AudioPlayer> audioPlayerProvider;

  private final Provider<RecordingRepository> recordingRepositoryProvider;

  private final Provider<TagRepository> tagRepositoryProvider;

  public PlayerViewModel_Factory(Provider<AudioPlayer> audioPlayerProvider,
      Provider<RecordingRepository> recordingRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    this.audioPlayerProvider = audioPlayerProvider;
    this.recordingRepositoryProvider = recordingRepositoryProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
  }

  @Override
  public PlayerViewModel get() {
    return newInstance(audioPlayerProvider.get(), recordingRepositoryProvider.get(), tagRepositoryProvider.get());
  }

  public static PlayerViewModel_Factory create(Provider<AudioPlayer> audioPlayerProvider,
      Provider<RecordingRepository> recordingRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    return new PlayerViewModel_Factory(audioPlayerProvider, recordingRepositoryProvider, tagRepositoryProvider);
  }

  public static PlayerViewModel newInstance(AudioPlayer audioPlayer,
      RecordingRepository recordingRepository, TagRepository tagRepository) {
    return new PlayerViewModel(audioPlayer, recordingRepository, tagRepository);
  }
}
