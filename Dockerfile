# Stage 1: Build the Android application
FROM gradle:8.5-jdk21 AS builder

# Install Android SDK
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator
ENV ANDROID_SDK_ROOT=${ANDROID_HOME}

RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    curl -L https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -o cmdline-tools.zip && \
    unzip -q cmdline-tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    rm cmdline-tools.zip && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest

RUN yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

WORKDIR /build
COPY CalculatorApp .
RUN gradle assemble --no-daemon

# Stage 2: Create a lightweight image with just the APK
FROM alpine:latest
WORKDIR /app
COPY --from=builder /build/app/build/outputs/apk/release/ .
RUN if [ ! -f *.apk ]; then echo "No release APK found"; exit 1; fi
CMD ["/bin/sh", "-c", "ls -lh *.apk && echo 'APK ready'"]
VOLUME [ "/data" ]
