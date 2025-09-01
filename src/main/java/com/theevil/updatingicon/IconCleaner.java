package com.theevil.updatingicon;

// File system utilities
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Fabric mod entry point
import net.fabricmc.api.ModInitializer;

// Event triggered when the server has finished starting
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

// Used to check if the server is running in singleplayer mode
import net.minecraft.server.integrated.IntegratedServer;

// Used to locate the world save folder
import net.minecraft.util.WorldSavePath;

public class IconCleaner implements ModInitializer {

	// Mod name used for logging
    public static final String MOD_NAME = "UpdatingIconMod";

    @Override
    public void onInitialize() {
		System.out.println("[" + MOD_NAME + "] Mod Initialized");
		// Register a callback for when the server has started
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			// Only run this logic in singleplayer (IntegratedServer)
			if (server instanceof IntegratedServer) {
				// Get the path to the world save folder
				Path worldFolder = ((IntegratedServer) server).getSavePath(WorldSavePath.ROOT);
				if (worldFolder == null) {
					System.err.println("[" + MOD_NAME + "] Could not resolve world folder path.");
					return;
				}
				// Resolve the path to the icon.png file
				Path iconPath = worldFolder.resolve("icon.png").normalize();

				try {
					// Attempt to delete the icon file if it exists
					Files.deleteIfExists(iconPath);
					System.out.println("[" + MOD_NAME + "] Removed old icon!");
				} catch (IOException e) {
					// Log any error that occurs during deletion
					System.err.println("[" + MOD_NAME + "] Error while deleting icon: " + e.getMessage());
				}
			}
		});
    }
}