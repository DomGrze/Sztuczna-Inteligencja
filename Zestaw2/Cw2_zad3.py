import math


class node():
  def __init__(self, value=None, rodzic=None):
    self.value = value
    self.rodzic = rodzic
    self.dziecko = []
    self.alpha = -math.inf
    self.beta = math.inf
    if rodzic != None:
      rodzic.dziecko.append(self)


def min(node): 
  for dziecko in node.dziecko:
    if dziecko.value > node.alpha:
      node.alpha = dziecko.value
      if node.alpha > node.rodzic.beta:
        return
      node.rodzic.alpha = node.alpha
  node.beta = node.alpha
  node.rodzic.beta = node.beta


def maximin(root): 
  for dziecko in root.dziecko:
    min(dziecko)

if __name__ == "__main__":

  
  A = node(None, None) 

  B = node(None, A)
  C = node(None, A)
  D = node(None, A)

  E = node(3, B)
  F = node(12, B)
  G = node(8, B)

  H = node(2, C)
  I = node(4, C) 
  J = node(6, C)

  K = node(14, D)
  L = node(5, D) 
  M = node(2, D)
  

  maximin(A)
  print ("A: [{0}, {1}]".format(A.alpha, A.beta))
  print ("B: [{0}, {1}]".format(B.alpha, B.beta))
  print ("C: [{0}, {1}]".format(C.alpha, C.beta))
  print ("D: [{0}, {1}]".format(D.alpha, D.beta))