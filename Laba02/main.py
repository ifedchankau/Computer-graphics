from os.path import join
from glob import glob
from PIL import Image
from sys import argv

images_folder = argv[1]
formats = ('*.png', '*.jpg', '*.gif', '*.bmp', '*.tif', '*.pcx')
mode_to_bd = {'1':1, 'L':8, 'P':8, 'RGB':24, 'YCbCr':24, 'RGBA':32, 'CMYK':32, 'F':32, 'I':32}
file_names = []

for files in formats:
    file_names.extend(glob(join(images_folder,files)))

for f in file_names:
    img = Image.open(f)
    print(f'File: {f}\n',
    	f'Size: {img.size} px\n',
    	f'DPI: {str(tuple(int(el) for el in img.info.get("dpi", ""))) if (img.info.get("dpi", "") != (0,0) and len(img.info.get("dpi", "")) > 0) else "N/A"}\n',
    	f'Bit Depth: {mode_to_bd[img.mode]}\n',
    	f'Compression: {img.info.get("compression", "") if img.info.get("compression", "") != "" else "N/A"}\n'
    	)
