// src/main/frontend/ws-jpeg-viewer.js
window.WsVideoViewer =  {
                         start(canvas, wsUrl) {
                           const ctx = canvas.getContext("2d");
                           const ws = new WebSocket(wsUrl);
                           ws.binaryType = "arraybuffer";

 ws.onopen = () => {
      canvas.$server.wsStatus("CONNECTED", null);
    };

    ws.onerror = () => {
          canvas.$server.wsStatus("ERROR", wsUrl);
        };

        ws.onclose = (e) => {
              canvas.$server.wsStatus("CLOSED", `code=${e.code} reason=${e.reason || ''}`);
            };

                           ws.onmessage = async (e) => {
                             const blob = new Blob([e.data], { type: "image/jpg" });
                             const bmp = await createImageBitmap(blob); // быстрый декодер
                             canvas.width = bmp.width;
                             canvas.height = bmp.height;
                             ctx.drawImage(bmp, 0, 0);
                             bmp.close?.();
                           };

                           return { stop: () => { try { ws.close(); } catch (e) {} } };
                         }
                       };