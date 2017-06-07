package com.parishram.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parishram.common.Constant;
import com.parishram.model.LoginContext;
import com.parishram.model.PhotoAlbumModel;
import com.parishram.service.AlbumService;
import com.parishram.service.ProfileService;
import com.parishram.utils.UploadPictures;

@SessionAttributes({ "loginContext"})
@Controller
public class AlbumnController {

	class Request {
		public Request(){} //default constructor
		private String response;
		Request(String resp) { //parameterized constructor
			this.response = resp;
		}
		public String getResponse() {
			return response;
		}
	}

	@Autowired
	private AlbumService albumService;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/album", method = RequestMethod.GET)
	public ModelAndView goToAlbumPage() {
		try {
			return new ModelAndView("album/album");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getAlbumId", method = RequestMethod.POST)
	public @ResponseBody Request getAlbumIdUsingAlbumName(@ModelAttribute("loginContext") LoginContext loginContext, @RequestBody PhotoAlbumModel model, HttpSession session) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			int albumId = albumService.getAlbumIdUsingAlbumName(model.getAlbumName());
			albumService.updateAlbumId(albumId, loginId);
			return new Request(Constant.SUCCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Request(Constant.FAILURE);
		}
	}

	@RequestMapping(value = "/getPhotosFromAlbum", method = RequestMethod.POST)
	public @ResponseBody PhotoAlbumModel getPhotosFromAlbum(HttpServletRequest request,
			@ModelAttribute("loginContext") LoginContext loginContext, @RequestBody PhotoAlbumModel model) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			List<Map<String, Object>> images = albumService.retrieveImagesFromAlbum(model.getAlbumId(), loginId);
			/*for (Map<String, Object> map : images) {
				String image = (String) map.get("image");
				int image_id = (Integer)map.get("image_id");
				
			}*/
			model.setImages(images);
			return model;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/uploadPhotosToAlbum", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Request uploadPhotosInAlbum(HttpServletRequest request, @ModelAttribute("loginContext") LoginContext loginContext) {
			try {
				String userName = loginContext.getUserName();
				int loginId = profileService.getLoginIdUsingUserName(userName);
				List<String> image = UploadPictures.uploadPictures(request);
				for (String img : image) {
					profileService.uploadImage(img, loginId, false,-1);
				}
			return new Request(Constant.SUCCESS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Request(Constant.FAILURE);
			}
	}

	@RequestMapping(value = "/rateAlbum", method = RequestMethod.POST)
	public void rateAlbum(@RequestBody PhotoAlbumModel model) {
		try {
			albumService.rateAlbum(model.getRating(), model.getAlbumId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getAlbumRating", method = RequestMethod.POST)
	public int getAlbumRating(@RequestBody PhotoAlbumModel model,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			int albumRating = albumService.getAlbumRating(model.getAlbumId(), loginId);
			return albumRating;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/deleteAlbum", method = RequestMethod.POST)
	public void deleteAlbum(@RequestBody PhotoAlbumModel model,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			albumService.deleteAlbum(model.getAlbumId(), loginId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getAllAlbums", method = RequestMethod.POST)
	public @ResponseBody List<PhotoAlbumModel> getAllAlbums(@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			List<PhotoAlbumModel> albums = albumService.getAllAlbums(loginId);
			return albums;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
	public @ResponseBody Request createPhotoAlbum(@ModelAttribute("loginContext") LoginContext loginContext,
			@RequestBody PhotoAlbumModel model) {
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			albumService.createAlbum(loginId, model.getAlbumName());
			Request req = new Request(Constant.SUCCESS);
			return req;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Request req = new Request(Constant.FAILURE);
			return req;
		}
	}
}
