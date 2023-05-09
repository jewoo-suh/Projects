import networkx as nx
from geopy.geocoders import Nominatim

# import pyperclip
from bokeh.io import show, output_file
from bokeh.plotting import figure
from bokeh.models import HoverTool, ColumnDataSource
import json

geolocator = Nominatim(user_agent="my_app_name")
def city_to_country(city):
    location = geolocator.geocode(city)
    return location.address.split(',')[-1].strip()


def return_user_dictionary():
    user_list = new_api.get_users()
    users = {
        i: dict(
            email=f"{user_list[i]['email']}", name=user_list[i]['name'],
            location= f"{city_to_country(user_list[i]['location'])}"
        ) for i in range(len(user_list))
    }
    return users

# Return Generated Tree
def generate_api_tree():
    # Code to bring API info
    #all_iters = new_api.get_all_info() #List format

    # Initialize nx Graph
    G = nx.DiGraph()


    # Add all nodes
    for node in all_iters:
        G.add_node(node['id'], **node)

    for n in G.nodes():
        #Needs fixing
        #G.nodes[n]['location'] = city_to_country(G.nodes[n]['location'])
        G.nodes[n]['location'] = "Poland"

    # Check each node's parent_iteration attribute and add edge accordingly
    for n in G.nodes():
        parent_id = G.nodes[n]['parent_iteration']
        if parent_id is not None:
            G.add_edge(parent_id, n)

    return G

"""
As the graph is currently in networkx, we use bokeh to plot the graph
(As networkx and bokeh go well together)

Essentially the main point is that the graph has hovertext,
which allows us to visually check node attributes
"""
def draw_networkx_graph():
    # Generate a layout for the graph
    pos = nx.spring_layout(G)

    # Create a list of node attributes
    node_attrs = []
    for n in G.nodes():
        attrs = G.nodes[n]
        tooltip = '<h4>Node {}</h4>'.format(n)
        tooltip += '<p><strong>Author Email:</strong> {}</p>'.format(attrs['author_email'])
        tooltip += '<p><strong>Author Name:</strong> {}</p>'.format(attrs['author_name'])
        tooltip += '<p><strong>Title:</strong> {}</p>'.format(attrs['title'])
        tooltip += '<p><strong>Problem:</strong> {}</p>'.format(attrs['problem'])
        tooltip += '<p><strong>Solution:</strong> {}</p>'.format(attrs['solution'])
        tooltip += '<p><strong>Context:</strong> {}</p>'.format(attrs['context'])
        tooltip += '<p><strong>Parent:</strong> {}</p>'.format(attrs['parent_iteration'])
        tooltip += '<p><strong>And many more attributes...</strong></p>'
        node_attrs.append((n, attrs['id'], tooltip))

    # Create a ColumnDataSource with the node positions and attributes
    source = ColumnDataSource(data={
        'x': [pos[n][0] for n in G.nodes()],
        'y': [pos[n][1] for n in G.nodes()],
        'id': [attr[1] for attr in node_attrs],
        'tooltip': [attr[2] for attr in node_attrs],
    })

    # Create the plot
    fig = figure(title='Graph with Tooltips', tools='hover, pan, wheel_zoom, reset', active_scroll='wheel_zoom')
    fig.toolbar.logo = None
    fig.toolbar_location = 'above'

    # Add the nodes to the plot
    circles = fig.circle(x='x', y='y', size=20, fill_color='yellow', line_color='black', source=source)

    # Add text labels to the nodes
    labels = fig.text(x='x', y='y', text='id', text_font_size='10pt', text_align='center', text_baseline='middle', source=source)

    # Add the tooltips to the nodes
    hover = fig.select(dict(type=HoverTool))
    hover.tooltips = '<div>' + '@tooltip' + '</div>'

    # Create a list of edge coordinates
    edge_xs = []
    edge_ys = []
    for start_node, end_node in G.edges():
        edge_xs.append([pos[start_node][0], pos[end_node][0]])
        edge_ys.append([pos[start_node][1], pos[end_node][1]])

    # Add the edges to the plot
    fig.multi_line(xs=edge_xs, ys=edge_ys, line_color='black', line_alpha=0.3, line_width=1)

    # Save and show the plot
    output_file('graph_with_tooltips.html')
    show(fig)



