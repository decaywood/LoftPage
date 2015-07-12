/**
 * Created by decaywood on 2015/7/2.
 */
function RemoteGameManager(size, Actuator, target) {
    this.size           = size; // Size of the grid
    this.actuator       = new Actuator(target);
    this.startTiles     = 2;
    this.updateIPAddress("");
}

// Restart the game
RemoteGameManager.prototype.restart = function (tiles, bestScore, IPAddress) {
    this.actuator.continueGame(); // Clear the game won/lost message
    this.setup(tiles, bestScore);
    this.updateIPAddress(IPAddress);
};

RemoteGameManager.prototype.updateIPAddress = function (IPAddress) {
    this.actuator.updateIPAddress(IPAddress);
};

// Keep playing after winning (allows going over 2048)
RemoteGameManager.prototype.keepPlaying = function () {
    this.keepPlaying = true;
    this.actuator.continueGame(); // Clear the game won/lost message
};

// Return true if the game is lost, or has won and the user hasn't kept playing
RemoteGameManager.prototype.isGameTerminated = function () {
    return this.over || (this.won && !this.keepPlaying);
};

// Set up the game
RemoteGameManager.prototype.setup = function (randomTiles, bestScore) {


    this.grid        = new Grid(this.size);
    this.score       = 0;
    this.bestScore   = bestScore;
    this.over        = false;
    this.won         = false;
    this.keepPlaying = false;

    // Add the initial tiles
    this.addStartTiles(randomTiles);


    // Update the actuator
    this.actuate();
};

// Set up the initial tiles to start the game with
RemoteGameManager.prototype.addStartTiles = function (randomTiles) {
    for (var i = 0; i < randomTiles.length; i++) {
        var randomTile = randomTiles[i];
        var x = randomTile.x;
        var y = randomTile.y;
        var position = {x: x, y: y};
        var tile = new Tile(position, randomTile.value);
        this.addRandomTile(tile);
    }
};

// Adds a tile in a random position
RemoteGameManager.prototype.addRandomTile = function (tile) {
    if (this.grid.cellsAvailable()) {
        this.grid.insertTile(tile);
    }
};

// Sends the updated grid to the actuator
RemoteGameManager.prototype.actuate = function () {

    if (this.bestScore < this.score) {
        this.bestScore = score;
    }

    this.actuator.actuate(this.grid, {
        score:      this.score,
        over:       this.over,
        won:        this.won,
        bestScore:  this.bestScore,
        terminated: this.isGameTerminated()
    });

};

// Represent the current game as an object
RemoteGameManager.prototype.serialize = function () {
    return {
        grid:        this.grid.serialize(),
        score:       this.score,
        over:        this.over,
        won:         this.won,
        keepPlaying: this.keepPlaying
    };
};

// Save all tile positions and remove merger info
RemoteGameManager.prototype.prepareTiles = function () {
    this.grid.eachCell(function (x, y, tile) {
        if (tile) {
            tile.mergedFrom = null;
            tile.savePosition();
        }
    });
};

// Move a tile and its representation
RemoteGameManager.prototype.moveTile = function (tile, cell) {
    this.grid.cells[tile.x][tile.y] = null;
    this.grid.cells[cell.x][cell.y] = tile;
    tile.updatePosition(cell);
};

// Move tiles on the grid in the specified direction
RemoteGameManager.prototype.move = function (direction, randomTile, bestScore) {

    this.bestScore = bestScore;
    // 0: up, 1: right, 2: down, 3: left
    var self = this;

    if (this.isGameTerminated()) return; // Don't do anything if the game's over

    var cell, tile;

    var vector     = this.getVector(direction);
    var traversals = this.buildTraversals(vector);
    var moved      = false;

    // Save the current tile positions and remove merger information
    this.prepareTiles();

    // Traverse the grid in the right direction and move tiles
    traversals.x.forEach(function (x) {
        traversals.y.forEach(function (y) {
            cell = { x: x, y: y };
            tile = self.grid.cellContent(cell);

            if (tile) {
                var positions = self.findFarthestPosition(cell, vector);
                var next      = self.grid.cellContent(positions.next);

                // Only one merger per row traversal?
                if (next && next.value === tile.value && !next.mergedFrom) {
                    var merged = new Tile(positions.next, tile.value * 2);
                    merged.mergedFrom = [tile, next];

                    self.grid.insertTile(merged);
                    self.grid.removeTile(tile);

                    // Converge the two tiles' positions
                    tile.updatePosition(positions.next);

                    // Update the score
                    self.score += merged.value;

                    // The mighty 2048 tile
                    if (merged.value === 2048) self.won = true;
                } else {
                    self.moveTile(tile, positions.farthest);
                }

                if (!self.positionsEqual(cell, tile)) {
                    moved = true; // The tile moved from its original cell!
                }
            }
        });
    });

    if (moved) {
        var x = randomTile.x;
        var y = randomTile.y;
        var position = {x: x, y: y};
        var t = new Tile(position, randomTile.value);

        this.addRandomTile(t);

        if (!this.movesAvailable()) {
            this.over = true; // Game over!
        }

        this.actuate();
    }
};

// Get the vector representing the chosen direction
RemoteGameManager.prototype.getVector = function (direction) {
    // Vectors representing tile movement
    var map = {
        0: { x: 0,  y: -1 }, // Up
        1: { x: 1,  y: 0 },  // Right
        2: { x: 0,  y: 1 },  // Down
        3: { x: -1, y: 0 }   // Left
    };

    return map[direction];
};

// Build a list of positions to traverse in the right order
RemoteGameManager.prototype.buildTraversals = function (vector) {
    var traversals = { x: [], y: [] };

    for (var pos = 0; pos < this.size; pos++) {
        traversals.x.push(pos);
        traversals.y.push(pos);
    }

    // Always traverse from the farthest cell in the chosen direction
    if (vector.x === 1) traversals.x = traversals.x.reverse();
    if (vector.y === 1) traversals.y = traversals.y.reverse();

    return traversals;
};

RemoteGameManager.prototype.findFarthestPosition = function (cell, vector) {
    var previous;

    // Progress towards the vector direction until an obstacle is found
    do {
        previous = cell;
        cell     = { x: previous.x + vector.x, y: previous.y + vector.y };
    } while (this.grid.withinBounds(cell) &&
    this.grid.cellAvailable(cell));

    return {
        farthest: previous,
        next: cell // Used to check if a merge is required
    };
};

RemoteGameManager.prototype.movesAvailable = function () {
    return this.grid.cellsAvailable() || this.tileMatchesAvailable();
};

// Check for available matches between tiles (more expensive check)
RemoteGameManager.prototype.tileMatchesAvailable = function () {
    var self = this;

    var tile;

    for (var x = 0; x < this.size; x++) {
        for (var y = 0; y < this.size; y++) {
            tile = this.grid.cellContent({ x: x, y: y });

            if (tile) {
                for (var direction = 0; direction < 4; direction++) {
                    var vector = self.getVector(direction);
                    var cell   = { x: x + vector.x, y: y + vector.y };

                    var other  = self.grid.cellContent(cell);

                    if (other && other.value === tile.value) {
                        return true; // These two tiles can be merged
                    }
                }
            }
        }
    }

    return false;
};

RemoteGameManager.prototype.positionsEqual = function (first, second) {
    return first.x === second.x && first.y === second.y;
};
