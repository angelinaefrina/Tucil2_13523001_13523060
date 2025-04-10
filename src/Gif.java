import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.IIOImage;
import strukturdata.Matriks;
import strukturdata.Pixel;

public class Gif {
    public static void createGif(QuadTreeNode root, int maxDepth, String outputPath) {
        try (ImageOutputStream output = ImageIO.createImageOutputStream(new File(outputPath))) {
            ImageWriter gifWriter = ImageIO.getImageWritersByFormatName("gif").next();
            gifWriter.setOutput(output);

            // Konfigurasi metadata untuk GIF animasi
            gifWriter.prepareWriteSequence(null);

            for (int depth = 0; depth <= maxDepth; depth++) {
                Matriks imageAtDepth = root.createImageAtDepth(depth);
                BufferedImage bufferedImage = convertMatriksToBufferedImage(imageAtDepth);

                // Buat metadata untuk frame
                IIOMetadata metadata = gifWriter.getDefaultImageMetadata(new javax.imageio.ImageTypeSpecifier(bufferedImage), null);
                configureMetadata(metadata, 500); // Delay 500ms per frame

                // Tulis frame ke GIF
                gifWriter.writeToSequence(new IIOImage(bufferedImage, null, metadata), null);
            }

            gifWriter.endWriteSequence();
            System.out.println("GIF berhasil dibuat di: " + outputPath);
        } catch (IOException e) {
            System.err.println("Gagal membuat GIF: " + e.getMessage());
        }
    }

    private static BufferedImage convertMatriksToBufferedImage(Matriks matriks) {
        int width = matriks.kolom;
        int height = matriks.baris;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Pixel p = matriks.mat[i][j];
                if (p == null) {
                    // Jika elemen null, gunakan warna default (hitam)
                    image.setRGB(j, i, 0);
                } else {
                    int rgb = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
                    image.setRGB(j, i, rgb);
                }
            }
        }
    
        return image;
    }

    private static void configureMetadata(IIOMetadata metadata, int delayTime) {
        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delayTime / 10)); // Delay dalam 1/100 detik
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode appExtension = new IIOMetadataNode("ApplicationExtension");
        appExtension.setAttribute("applicationID", "NETSCAPE");
        appExtension.setAttribute("authenticationCode", "2.0");
        appExtension.setUserObject(new byte[] { 0x1, 0x0, 0x0 });
        appExtensionsNode.appendChild(appExtension);

        try {
            metadata.setFromTree(metaFormatName, root);
        } catch (Exception e) {
            throw new RuntimeException("Gagal mengatur metadata GIF: " + e.getMessage());
        }
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        for (int i = 0; i < rootNode.getLength(); i++) {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return (IIOMetadataNode) rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return node;
    }
}