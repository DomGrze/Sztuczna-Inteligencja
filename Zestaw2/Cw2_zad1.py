
class Pokoj:
    def __init__ (self, name, jestBrudny=1):
        
        self.name = name
        self.jestBrudny = jestBrudny


class Robot:
    def __init__ (self):
       
        self.pokoj_lewy = Pokoj("A")
        self.pokoj_prawy = Pokoj("B")
        self.pokoj_obecny = self.pokoj_lewy

    def idz_lewo(self):
       
        if self.pokoj_obecny == self.pokoj_prawy:
            self.pokoj_obecny = self.pokoj_lewy

    def idz_prawo(self):
       
        if self.pokoj_obecny == self.pokoj_lewy:
            self.pokoj_obecny = self.pokoj_prawy

    def czysc(self):
        
        self.pokoj_obecny.jestBrudny = 0

    def auto(self):
        
        counter = 0

        while self.pokoj_lewy.jestBrudny == 1 or self.pokoj_prawy.jestBrudny == 1:
            if self.pokoj_obecny.jestBrudny:
                self.czysc()
            elif self.pokoj_obecny == self.pokoj_lewy:
                self.idz_prawo()
            elif self.pokoj_obecny == self.pokoj_prawy:
                self.idz_lewo

            counter = counter + 1
            print("Step: {0}".format(counter))
            print(self)

        return counter

    def __str__(self):
        return "Obecny pokoj: {0}, {1} brod: {2}, {3} brod: {4}".format(self.pokoj_obecny.name,
                                                                        self.pokoj_lewy.name, self.pokoj_lewy.jestBrudny,
                                                                        self.pokoj_prawy.name, self.pokoj_prawy.jestBrudny)


class Stan:
    def __init__ (self, pokoj_obecny, dirt_on_left, dirt_on_right):
       
        self.pokoj_obecny = pokoj_obecny
        self.dirt_on_left = dirt_on_left
        self.dirt_on_right = dirt_on_right

    def __str__(self):
        return "Robot w pokoju: {0}, brod w A: {1}, brod w B: {2}".format(self.pokoj_obecny, self.dirt_on_left, self.dirt_on_right)


class Graph:
    
    def __init__ (self):
        self.A = Stan("A", 1, 1)
        self.B = Stan("B", 1, 1)
        self.C = Stan("A", 0, 1)
        self.D = Stan("B", 1, 0)
        self.E = Stan("B", 0, 1)
        self.F = Stan("A", 1, 0)
        self.G = Stan("B", 0, 0)
        self.H = Stan("A", 0, 0)

        self.graph = {
            self.A: [self.B, self.C],
            self.B: [self.A, self.D],
            self.C: [self.A, self.E],
            self.D: [self.B, self.F],
            self.E: [self.C, self.G],
            self.F: [self.D, self.H],
            self.G: [self.H],
            self.H: [self.G]
        }

        self.visited = []
        self.queue = []

    def bfs(self):
        self.visited.append(self.A)
        self.queue.append(self.A)

        while self.queue:
            s = self.queue.pop(0)
            print(s)

            for neighbour in self.graph[s]:
                if neighbour not in self.visited:
                    self.visited.append(neighbour)
                    self.queue.append(neighbour)


if __name__ == '__main__':

    # robot = Robot()
    # print(robot.auto())

    g = Graph()
    g.bfs()