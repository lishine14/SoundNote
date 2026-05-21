package com.soundnote.di;

import android.content.Context;
import com.soundnote.service.AudioPlayer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ServiceModule_ProvideAudioPlayerFactory implements Factory<AudioPlayer> {
  private final Provider<Context> contextProvider;

  public ServiceModule_ProvideAudioPlayerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AudioPlayer get() {
    return provideAudioPlayer(contextProvider.get());
  }

  public static ServiceModule_ProvideAudioPlayerFactory create(Provider<Context> contextProvider) {
    return new ServiceModule_ProvideAudioPlayerFactory(contextProvider);
  }

  public static AudioPlayer provideAudioPlayer(Context context) {
    return Preconditions.checkNotNullFromProvides(ServiceModule.INSTANCE.provideAudioPlayer(context));
  }
}
