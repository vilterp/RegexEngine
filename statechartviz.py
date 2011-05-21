graph = ximport('graph')

path = '/Users/petevilter/Dropbox/code/vmlang/examples/fib.vmlcg'

cgtuples = [l.split('\t') for l in open(path).read().split('\n') if l != '']

print cgtuples

speed(30)

def setup():
    global g, cgtuples
    g = graph.create()

    for tuple in cgtuples:
        g.add_node(tuple[0])
        g[tuple[0]].id = tuple[0]

    for tuple in cgtuples:
        g.add_edge(tuple[0],tuple[1])
    
    g['main'].style = 'important'
    
    g.prune()

def draw():
    global g
    g.draw(directed=True)
