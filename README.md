# Adventure Game

A retro-style 2D adventure game with multiplayer functionality, built using Java. This game allows players to explore a pixelated world and interact with objects.

## Features
- **Single-player and Multiplayer Modes** - Play solo or connect with other players over a network.
- **Java Socket-based Networking** - Enables real-time communication between players.
- **Pixel Art Style Graphics** - Retro-style visuals for a nostalgic gaming experience.
- **Real-time Player Interaction** - Players can interact with each other and the game world.
- **Explorable Map and Objects** - A detailed game world filled with interactive elements.
- **Smooth Character Movement** - Implemented using a game loop for responsive controls.
- **Basic AI for NPCs** - Non-playable characters have scripted behaviors and interactions.
- **Inventory and Item Collection System** - Players can collect, store, and use items.
- **Collision Detection** - Prevents players from moving through walls and obstacles.
- **Game Events and Triggers** - Certain actions trigger specific in-game events.

## Technologies and Libraries Used
- **Java** - Core language for game development, handling game logic and object management.
- **Java Swing** - Used for rendering the UI elements, game menus, and HUD.
- **Java AWT (Abstract Window Toolkit)** - Handles rendering and drawing of graphics, such as sprites and maps.
- **Java Sockets** - Facilitates multiplayer communication, allowing real-time data exchange between the server and clients.
- **Java Threads** - Used for concurrency to handle multiple players efficiently in multiplayer mode.
- **Object-Oriented Programming (OOP)** - Structured and modular design to keep the game components organized.
- **Serialization** - Enables saving and loading of game data, such as player progress and game state.
- **File I/O** - Reads assets (maps, sprites) from external files and stores progress.
- **Sprite Sheets** - Used for animations and efficient rendering of characters and objects.
- **Game Loop** - Ensures smooth frame updates for animation, movement, and logic.
- **Event-driven Programming** - Handles player input, NPC interactions, and item usage.

## Installation

### Prerequisites
- Java 11 or later
- Git

### Clone the Repository
```sh
git clone https://github.com/P3R5EUS/AdventureGame.git
cd AdventureGame
```

### Running the Game
#### Single-player Mode
Run the main class in your IDE or use the command:
```sh
java -jar AdventureGame.jar
```

#### Multiplayer Mode
**Start the Server:**
```sh
java -cp bin com.game.server.GameServer
```
**Start the Client:**
```sh
java -cp bin com.game.client.GameClient
```

## Implementation Details

### Graphics & UI:
- Java Swing and AWT are used to render pixel-style graphics.
- Game objects and characters are drawn onto a canvas.
- Animations are implemented using sprite sheets.
- The UI includes an interactive HUD, menus, and inventory display.

### Multiplayer Functionality:
- Java Sockets handle real-time communication.
- A central server synchronizes player actions and game state.
- Clients send and receive data packets to update player positions, interactions, and events.
- Multithreading ensures smooth gameplay by handling multiple clients simultaneously.

### Game Physics & Movement:
- Implemented a game loop to update movement and physics at a consistent frame rate.
- Collision detection prevents players from walking through walls or objects.
- Pathfinding is used for NPC movement in certain scenarios.

### Inventory System:
- Players can collect, store, and use various in-game items.
- Items can trigger special actions, such as unlocking doors or boosting player stats.

### Basic AI for NPCs:
- NPCs have predefined movement patterns.
- Some NPCs respond dynamically based on player actions, such as engaging in dialogue or initiating quests.

### Game Events & Triggers:
- Players can activate hidden events by interacting with objects or NPCs.
- Certain areas may unlock upon completing specific tasks.

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a pull request

## License
This project is licensed under the MIT License.

## Contact
For any inquiries, feel free to reach out via GitHub Issues.

## Team Members
1. Panshull Choudhary - [P3R5EUS](https://github.com/P3R5EUS)
2. Vanshika Agarwal - [vanshikagarwal15](https://github.com/vanshikagrawal15)
3. Atharva Rai - [AtharvaRai-ctrl](https://github.com/AtharvaRai-ctrl)

